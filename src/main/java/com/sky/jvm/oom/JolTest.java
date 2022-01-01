package com.sky.jvm.oom;


import org.openjdk.jol.info.ClassLayout;
import sun.misc.Lock;


public class JolTest {
    static Lock lock = new Lock();

    public static void main(String[] args) throws InterruptedException {
//        System.out.println(ClassLayout.parseClass(Lock.class).toPrintable());


        Thread t1 = new Thread(()->{
            test();
        });
        Thread t2 = new Thread(()->{
            test();
        });
        t1.start();
//        t1.join(); //打开注释为轻量级锁
        t2.start();

    }

    static void test() {
        synchronized (lock) {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName());
                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
