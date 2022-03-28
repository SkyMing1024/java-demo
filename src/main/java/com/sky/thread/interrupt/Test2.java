package com.sky.thread.interrupt;

public class Test2 {
    public static void main(String args[]){
        Thread thread = Thread.currentThread();
        System.out.println("isInterrupted 1->"+thread.isInterrupted());
        System.out.println("stop 0->" + Thread.interrupted());
        thread.interrupt();
        System.out.println("isInterrupted 2->"+thread.isInterrupted());
        System.out.println("stop 1->" + Thread.interrupted());
        System.out.println("isInterrupted 3->"+thread.isInterrupted());
        System.out.println("stop 2->" + Thread.interrupted());
        System.out.println("isInterrupted 4->"+thread.isInterrupted());
        System.out.println("End");
    }
}
