package com.sky.thread.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomicInteger {
    private static AtomicInteger n;

    public static void main(String[] args) throws InterruptedException {
        int j = 0;
        while (j < 1000) {
            n = new AtomicInteger(0);
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        n.getAndIncrement();
                    }
                }
            });

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        n.getAndIncrement();
                    }
                }
            });

            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println("n的最终值：" + n.get());
            j++;
        }

    }
}
