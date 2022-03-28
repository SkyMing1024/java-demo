package com.sky.thread.p1_basic;

public class Test4Join {

    public static void main(String[] args) {
        int cnt = 10;

        Thread t1 = new Thread(() -> {
            String name = Thread.currentThread().getName();
            for (int i = 0; i < cnt; i++) {
                System.out.println(name+"  "+i);
            }
        },"t1");

        Thread t2 = new Thread(() -> {

            String name = Thread.currentThread().getName();
            for (int i = 0; i < cnt; i++) {
                if (i == cnt/2){
                    try {
                        t1.join();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(name+"  "+" i:"+ i +"  "+((char) i));
            }


        },"t2");

        t2.start();
        t1.start();
    }
}
