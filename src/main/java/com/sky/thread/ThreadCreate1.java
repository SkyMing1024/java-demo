package com.sky.thread;

/**
 * 多线程的实现方式1/3
 * 继承 Thrend 类
 */
public class ThreadCreate1 extends Thread {
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        for (int i = 0; i < 100; i++) {
            System.out.println(threadName+"-"+i);
        }
    }

    public static void main(String[] args) {
        // 创建线程
        Thread thread1= new ThreadCreate1();
        Thread thread2= new ThreadCreate1();
        thread1.start();
        thread2.start();
    }
}
