package top.softone.freedom.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author luojian
 * @version 1.0
 * @date 2021年03月10日 14:24
 * 三个线程交替输出 AABBCCCAABBCCC 循环下去
 */
public class Test2 {

    private static Test2 object = new Test2();
    private int status = 1;

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // 线程A
        Runnable A = () -> {
            while (true) {
                synchronized (object) {
                    if (object.status != 1) {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.print("AA");
                        object.status =2;
                        object.notify();
                    }

                }
            }
        };

        Runnable B = () -> {
            while (true) {
                synchronized (object) {
                    if (object.status != 2) {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.print("BB");
                        object.status = 3;
                        object.notify();
                    }
                }
            }
        };

        Runnable C = () -> {
            while (true) {
                synchronized (object) {
                    if (object.status != 3) {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.print("CCC");
                        object.status = 1;
                        object.notify();
                    }
                }
            }
        };
        executorService.execute(A);
        executorService.execute(B);
        executorService.execute(C);
    }

}
