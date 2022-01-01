package com.sky.thread;

/**
 * 创建线程的方式2/3
 * 实现Runable接口，并重写run()方法
 */
public class ThreadCreate2 implements Runnable{
    @Override
    public void run() {
        int a = 0;
        String threadName = Thread.currentThread().getName();
        for (int i = 0; a < 100; a++) {
            System.out.println(threadName+"-"+a);
        }
    }

    public static void main(String[] args) {
        ThreadCreate2 runable = new ThreadCreate2();
        Thread t1 = new Thread(runable);
        Thread t2 = new Thread(runable);
        t1.start();
        t2.start();
    }
}
