package com.sky.thread.p1_basic;

public class Test_Join_3 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            String name = Thread.currentThread().getName();
            for (int i = 0; i < 120; i++) {
                System.out.println(name+ ":" + i);
            }
        },"t1");

        Thread t2 = new Thread(() -> {
            String name = Thread.currentThread().getName();
            for (int i = 0; i < 100; i++) {
                System.out.println(name+ ":" + "a");
            }

        },"t2");

        t1.start();
        t2.start();

    }
}
