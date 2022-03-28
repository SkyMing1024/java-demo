package com.sky.thread.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/***********************************************************************************************************************
举例：
 一共n名运动员，1名教练员，所有运动员准备完毕后，教练才开始讲话
***********************************************************************************************************************/
public class CoachRacerDemo {
    ReentrantLock lock = new ReentrantLock();

    private CountDownLatch countDownLatch = new CountDownLatch(3);

    public void racer(){

        // 1.获取名称
        String name = Thread.currentThread().getName();
        // 2.开始准备
        System.out.println(name+"： 正在准备......");
        // 3.休眠100ms,准备中
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 4.准备完毕
        System.out.println(name+"： 准备完毕!");
        countDownLatch.countDown();
    }

    public void coach(){
        // 1.获取名称
        String name = Thread.currentThread().getName();
        // 2.等待所有运动员准备完毕：打印等待信息
        System.out.println(name+"等待运动员："+countDownLatch.getCount());
        // 3.调用   等待其他线程结束
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 4.所有准备就绪，教练开始：打印训练信息
        System.out.println(name+"： 所有运动员准备完毕");

    }

    public static void main(String[] args) {
        CoachRacerDemo demo = new CoachRacerDemo();
        // 创建三个线程，表示运动员
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                demo.racer();
            }
        },"运动员1");
        Thread t2 = new Thread(new Racer(demo.countDownLatch),"运动员2");
        Thread t3 = new Thread(()->demo.racer(),"运动员3");

        // 创建一个线程，表示教练
        Thread t4 = new Thread(()->demo.coach(),"教练");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

class Racer implements Runnable{
    CountDownLatch countDownLatch;

    public Racer(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        // 1.获取名称
        String name = Thread.currentThread().getName();
        // 2.开始准备
        System.out.println(name+"正在准备......");
        // 3.休眠100ms,准备中
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 4.准备完毕
        System.out.println(name+"准备完毕!");
        countDownLatch.countDown();
    }
}