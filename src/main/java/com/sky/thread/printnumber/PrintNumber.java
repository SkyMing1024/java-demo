package com.sky.thread.printnumber;

/**
 * 两个线程共同打印1-1000的数
 */
public class PrintNumber {

    public static void main(String[] args) {

        Print print = new Print();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                print.printNumber();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                print.printNumber();
            }
        });

        t1.start();
        t2.start();
    }


}


class Print{
    int num = 0;
    public void printNumber() {
        for (int i = 0; num < 1000; num++) {
            synchronized (this){
                System.out.println(Thread.currentThread().getName()+" ： "+num);

            }
        }
    }
}