package com.sky.thread.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;


/**
 * 7个线程同时寻找7颗龙珠，都找齐后宣布找到了
 */
public class FindDragonBall {
    static class MyThread implements Runnable{
        CountDownLatch latch;
        public MyThread(CountDownLatch latch) {
            this.latch = latch;
        }
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name + "开始寻找");
            int millis = 2 * new Random().nextInt(1000);
            try {

                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "找到了"+"耗时：" + millis+ "  剩余："+ latch.getCount() );

            latch.countDown();

        }
    }
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(7);
        for (int i = 0; i < 7; i++) {
            int no = i+1;
            Thread t = new Thread(new MyThread(latch),"T-"+no);
            t.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("找到所有龙珠了");
    }


}
