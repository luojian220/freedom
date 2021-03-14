package top.softone.freedom.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author luojian
 * @version 1.0
 * @date: 2021年03月14日 12:18
 * @since JDK 1.8
 * 三个线程交替输出 AABBCCCAABBCCC 循环下去
 */
public class SyncAndReentrantLockDemo {

    public static void main(String[] args) {
        ShareResource shareResource = new SyncAndReentrantLockDemo.ShareResource();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.printAA();
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.printBB();
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.printCC();
            }
        }, "CCC").start();
    }

    static class ShareResource {
        private int number = 1;
        private Lock lock = new ReentrantLock();
        private Condition condition1 = lock.newCondition();
        private Condition condition2 = lock.newCondition();
        private Condition condition3 = lock.newCondition();

        public void printAA() {
            lock.lock();
            try {
                while (number != 1) {
                    condition1.await();
                }
                System.out.print(Thread.currentThread().getName());
                number = 2;
                condition2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }


        public void printBB() {
            lock.lock();
            try {
                while (number != 2) {
                    condition2.await();
                }
                System.out.print(Thread.currentThread().getName());
                number = 3;
                condition3.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }


        public void printCC() {
            lock.lock();
            try {
                while (number != 3) {
                    condition3.await();
                }
                System.out.print(Thread.currentThread().getName());
                number = 1;
                condition1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
