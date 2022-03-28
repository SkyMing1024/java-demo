package com.sky.book_billion_traffic.ch03_thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoopPrintTest {
    static volatile int num = 0;
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
//        Thread t1 = new Thread(new LoopPrint123_1(lock));
//        Thread t2 = new Thread(new LoopPrint123_1());
//        Thread t3 = new Thread(new LoopPrint123_1());
//        t2.start();
//        t1.start();
//        t3.start();


    }
}
