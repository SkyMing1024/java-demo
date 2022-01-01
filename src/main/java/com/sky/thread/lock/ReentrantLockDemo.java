package com.sky.thread.lock;

import java.util.Hashtable;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁演示
 */
public class ReentrantLockDemo {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0; i < 10; i++) {
            reentrantLock.lock();
            System.out.println("加锁次数："+(i+1));
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("解锁："+(i+1));
            reentrantLock.unlock();
        }

    }
}
