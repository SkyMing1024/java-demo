package com.sky.thread;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 借助 openjdk 提供的 jol-core ,可以更加直观的看到对象结构
 */
public class JolTest {

    public static void main(String[] args) {
        Object obj = new Object();
        ReentrantLock lock = new ReentrantLock();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        lock.lock();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        lock.unlock();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    }
}
