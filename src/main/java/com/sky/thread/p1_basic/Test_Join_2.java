package com.sky.thread.p1_basic;


/*
t2执行到一半时，t1执行，之后t2继续执行
 */
public class Test_Join_2 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            String name = Thread.currentThread().getName();
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name+ ":" + i);
            }
        },"t1");

        Thread t2 = new Thread(() -> {
            String name = Thread.currentThread().getName();
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                    if (i==5){
                        System.out.println("t2线程暂停");
                        t1.join(8000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(name+ ":" + "a");
            }

        },"t2");

        t1.start();
        t2.start();

    }
}
