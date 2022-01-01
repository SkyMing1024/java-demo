package com.sky.thread.printnumber;

/**
 * 两个线程交替打印数字
 * 用Object()下 wait notify 方法实现
 */
public class PrintOddEvenNumber {

    private int i =0;

    public void printOdd(){
        String name = Thread.currentThread().getName();
        while (i < 100){
            synchronized (this){
                if (i%2==1){
                    System.out.println(name+" 奇数: "+i);
                    i++;
                    this.notify();
                }else {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }

    public void printEven(){
        String name = Thread.currentThread().getName();
        while (i < 100){
            synchronized (this){
                if (i%2==0){
                    System.out.println(name+" 偶数："+i);
                    i++;
                    this.notify();
                }else {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    public static void main(String[] args) {
        PrintOddEvenNumber print = new PrintOddEvenNumber();
        // 奇数线程
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                print.printOdd();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                print.printEven();
            }
        });
        t1.start();
        t2.start();
    }
}
