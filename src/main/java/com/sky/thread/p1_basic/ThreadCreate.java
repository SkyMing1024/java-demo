package com.sky.thread.p1_basic;

import lombok.SneakyThrows;
import org.apache.poi.ss.formula.functions.T;

public class ThreadCreate {

    public static void main(String[] args) {

        new MyThread1().start();

        MyRunable myRunable = new MyRunable();
        new Thread(myRunable,"thread-runable").start();

        new Thread() {
            @SneakyThrows
            @Override
            public void run() {

                System.out.println(Thread.currentThread().getName());
            }
        }.start();

         new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
             try {
                 System.out.printf("zhuangtai:"+Thread.currentThread().getState());
                 Thread.sleep(100);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         },"thread-lamda").start();
    }

}
class MyThread1 extends Thread{
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName);
    }

}

 class MyRunable implements Runnable {
     @SneakyThrows
     @Override
     public void run() {
         Thread.sleep(100);
         System.out.println(Thread.currentThread().getName());
     }
 }
