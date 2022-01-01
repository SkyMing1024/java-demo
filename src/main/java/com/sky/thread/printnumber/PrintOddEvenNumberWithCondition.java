package com.sky.thread.printnumber;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程交替打印数字
 * 用Condition实现
 */
public class PrintOddEvenNumberWithCondition {
    private int i = 0;
    // 独占锁
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void printOdd() {
        while (i < 100){
//            synchronized (this)
            lock.lock();
            try {
                if (i%2==1){
                    System.out.println(" 奇数: "+i);
                    i++;
//                    this.notify();
                    condition.signal();
                }else {
                    condition.await();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }


    public void printEven() {
        while (i < 100){
//            synchronized (this)
            lock.lock();
            try {
                if (i%2==0){
                    System.out.println(" 偶数: "+i);
                    i++;
//                    this.notify();
                    condition.signal();
                }else {
                    condition.await();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        PrintOddEvenNumberWithCondition print = new PrintOddEvenNumberWithCondition();
        // 奇数线程
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                print.printOdd();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                print.printEven();
            }
        });
        t1.start();
        t2.start();
    }

}
