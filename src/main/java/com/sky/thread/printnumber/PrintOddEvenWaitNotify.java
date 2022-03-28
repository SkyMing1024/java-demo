package com.sky.thread.printnumber;

import com.sky.util.Debug;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;


/**
 * 1. 不能只用volatile，因为它只能保证可见性，无法保证原子性
 * 2. 不能用只用AtomicInteger，无法保证顺序
 * 3. 「顺序打印」问题，都要结合线程协作机制，等待/唤醒，wait/notify/notifyAll，park/unpark,wait/signal
 */
public class PrintOddEvenWaitNotify {

    private static final Object lock = new Object();
    private static int type = 1;
    private  static volatile AtomicInteger num = new AtomicInteger();

    static Thread t1 = null,t2 = null;


    public static void main(String[] args) {

        // 奇数
         t1 = new Thread(() -> {
            while (true && num.get()<=10000){
                if (num.get()%2 == 1){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    String name = Thread.currentThread().getName();
                    System.out.println(name +" ： "+ num.getAndIncrement());
                    // 唤醒另外一个线程
                    LockSupport.unpark(t2);
                    // 将自己阻塞
                    LockSupport.park();
                }
            }
        },"奇数");

        // 偶数
         t2 = new Thread(() -> {
             while (true && num.get()<=10000){
                if (num.get()%2 == 0){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    String name = Thread.currentThread().getName();
                    System.out.println(name +" ： "+ num.getAndIncrement());
                    LockSupport.unpark(t1);
                    LockSupport.park();
                }
            }
        },"偶数");

        t1.start();
        t2.start();

    }


}
