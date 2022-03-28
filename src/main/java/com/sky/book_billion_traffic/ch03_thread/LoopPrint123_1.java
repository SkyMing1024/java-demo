package com.sky.book_billion_traffic.ch03_thread;


import org.apache.poi.ss.formula.functions.T;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LoopPrint123_1 {
    public static void main(String[] args) {
        Object lock = new Object();
        Thread t1 = null,t2 = null,t3 = null;
        t1 = new Thread(() -> {
            while (true){
                synchronized (lock){
                    String name = Thread.currentThread().getName();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name+" ："+ "A");
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t2 = new Thread(() -> {
            while (true){
                synchronized (lock){
                    String name = Thread.currentThread().getName();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name+" ："+ "B");
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t3 = new Thread(() -> {
            while (true){
                synchronized (lock){
                    String name = Thread.currentThread().getName();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name+" ："+ "C");
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();

    }
}
