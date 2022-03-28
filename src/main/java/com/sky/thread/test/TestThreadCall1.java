package com.sky.thread.test;

public class TestThreadCall1 {
    static volatile boolean flag = false;
    int cnt = 0;
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true){
                try {
//                    Thread.sleep(1000);
                    System.out.println("flag:"+flag);

                    if (flag){
                        for (int i = 0; i < 100; i++) {
                            System.out.println(i);
                            Thread.sleep(1000);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            System.out.println("等待2s");
            try {
                Thread.sleep(2000);
                flag = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }
}
