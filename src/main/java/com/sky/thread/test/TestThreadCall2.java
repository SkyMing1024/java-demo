package com.sky.thread.test;

public class TestThreadCall2 {
    private static Object lock = new Object();
    private static boolean flag = false;



    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (!flag) {
                    System.out.println("不能打印");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while (true) {
                    System.out.println("a");
                }
            }
        });


        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                flag = true;
                lock.notify();
            }
        });

        t1.start();
        for (int i = 5; i >0; i--) {
            System.out.println("倒计时："+i);
            Thread.sleep(1000);
        }
        t2.start();
    }
}
