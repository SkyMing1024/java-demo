package com.sky.threadpool;

import java.util.concurrent.*;

public class TestThreadPool {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println();
            }
        });

    }
}
