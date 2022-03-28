package com.sky.thread.interrupt;

import lombok.SneakyThrows;

public class Test3 {

    public static void main(String[] args) throws InterruptedException {
//        MyThread1 t1 = new MyThread1();
//        t1.start();
//        Thread.sleep(2000);
//        System.out.println("2秒到，线程被中断");
//        t1.interrupt();

        
        String a = "abc";
        String b = null;
        String c = "df";

        boolean b1 = (b=c) == null;

        System.out.println(b1);
    }

    /**
     * 线程不停打印数字，主线程在2秒后将其中断
     */
    static class MyThread1 extends Thread{
        @SneakyThrows
        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            String threadName = thread.getName();
            int cnt = 0;
            while (true){
                if (thread.isInterrupted()){
                    System.out.println("线程中断，停止运行");
                    break;
                }
                System.out.println(threadName + " - " + cnt++);
            }
        }
    }
}



