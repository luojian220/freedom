package top.softone.freedom.config;

import java.io.IOException;

/**
 * @author luojian
 * @version 1.0
 * @date: 2021年03月09日 21:43
 * @since JDK 1.8
 */
public class Test1 {


    private int num = 8;

    public Test1() {
        System.out.println("执行构造方法。。。。。start");
        new Thread(() -> System.out.println(num)).start();
        System.out.println("执行构造方法。。。。。end");
    }

    public static void main(String[] args) throws IOException {
        Test1 test1 = new Test1();
        System.in.read();

    }
}
