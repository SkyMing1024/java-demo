package com.sky.thread.p1_basic;

import lombok.SneakyThrows;

public class TheadState {
    public static Object a = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
                    synchronized (a) {
                        System.out.println(Thread.currentThread().getName() + "=== 获取到锁对象，调用wait方法，进入waiting状态，释放锁对象");
                        a.wait();
                        System.out.println(Thread.currentThread().getName() + "=== 从waiting状态醒来，获取到锁对象，继续执行了");

                    }
                }
            }
        }, "等待线程t1");

        Thread t2 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "------ 等待5秒钟");
                Thread.sleep(5000);
                synchronized (a) {
                    System.out.println(Thread.currentThread().getName() + " ----- 获取到锁对象,调用notify方法，释放锁对象");
                    a.notify();
                }
            }
        }, "唤醒线程t2");
        t1.start();
        t2.start();
    }

}

