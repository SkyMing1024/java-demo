package com.sky.thread.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

public class TestCyclicBarrier {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(5);

        for (int i = 0; i < 5; i++) {
            new Thread(new Player(barrier),"运动员"+i).start();
        }


    }

}

class Player implements Runnable{
    CyclicBarrier barrier;

    public Player(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            String name = Thread.currentThread().getName();
            System.out.println(name+" 正在准备");
            Thread.sleep(3000);
            System.out.println(name+" 已准备好");
            barrier.await();
            System.out.println(name+" 开始训练");
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
