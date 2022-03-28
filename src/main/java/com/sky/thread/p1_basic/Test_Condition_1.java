package com.sky.thread.p1_basic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test_Condition_1 {
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    public void methodAwait(){
        lock.lock();
        try {
            while (true) {
                condition.wait();
                condition.await();
            }
            // 执行目标动作
//            doAction();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void methodSignal(){
        lock.lock();
        try {
//            updateState();
            condition.signal();
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Condition condition1 = lock.newCondition();
    }


}
