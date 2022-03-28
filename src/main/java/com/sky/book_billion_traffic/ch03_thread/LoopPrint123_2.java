package com.sky.book_billion_traffic.ch03_thread;

import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 3个线程按顺序打印ABC
 */
public class LoopPrint123_2 extends Thread{


    String[] arr = {"A","B","C"};
    private static AtomicInteger atomI = new AtomicInteger();


    @SneakyThrows
    @Override
    public void run() {
        while (true){
            synchronized (atomI){
                Thread thread = Thread.currentThread();
                int i = atomI.get() % 3;
                if (arr[i].equals(thread.getName())){
                    atomI.incrementAndGet();
                    Thread.sleep(1000);
                    System.out.println(thread.getName() +" : "+ arr[i] + "    =========     " + i);
                    // 唤醒其他线程
                    atomI.notifyAll();
                }else {
                    // 如果顺序不一致，继续等待
                    atomI.wait();
                }
            }
        }
    }

    public static void main(String[] args) {
        LoopPrint123_2 t1 = new LoopPrint123_2();
        t1.setName("A");
        LoopPrint123_2 t2 = new LoopPrint123_2();
        t2.setName("B");
        LoopPrint123_2 t3 = new LoopPrint123_2();
        t3.setName("C");

        t1.start();
        t2.start();
        t3.start();

    }

}
