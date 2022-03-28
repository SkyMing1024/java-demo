package com.sky.thread.semaphore;

import com.sky.util.Debug;
import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.Semaphore;


/*
模拟停车场入场案例，一个10个车位，有余量时，车辆才可以进场
 */
public class TestSemaphore {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    String name = Thread.currentThread().getName();
                    Debug.info("========"+name+" 尝试入场");
                    System.out.println("剩余车位："+semaphore.availablePermits());
                    semaphore.acquire();
                    // 入场
                    Debug.info(name+" 入场");
                    Thread.sleep(new Random().nextInt(1000));
                    Debug.info(name+ " 出场");
                    semaphore.release();
                }
            },"车辆"+i).start();
        }
    }
}
