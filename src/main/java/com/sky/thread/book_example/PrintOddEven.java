package com.sky.thread.book_example;

public class PrintOddEven {
    static int num = 0;
    public static void main(String[] args) {
//        test1();
        Thread t0 = new Thread(new Print(0),"偶数t0");
        Thread t1 = new Thread(new Print(1),"奇数t1");

        t0.start();
        t1.start();

    }

    // 交替打印奇偶  synchronized
    private static void test1() {
        int cnt = 100;
        final Object LOCk = new Object();
        Thread t1 = new Thread(() -> {
            while (num<cnt){
                synchronized (LOCk){
                    if ((num & 1)  ==0){
                        System.out.println(Thread.currentThread().getName()+" : "+ num);
                        num++;
                    }
                }
            }
        },"偶数——t0");

        Thread t2 = new Thread(() -> {
            while (num<cnt){
                synchronized (LOCk){
                    if ((num & 1)==1){
                        System.out.println(Thread.currentThread().getName()+" : "+ num);
                        num++;
                    }
                }
            }
        },"奇数——t1");

        t1.start();
        t2.start();
    }
}

class Print implements Runnable{
    int cnt = 0;
    int type = 0;

    public Print(int type) {
        this.type = type;
    }

    @Override
    public void run() {
        while (cnt<100){
            if ( type ==1 && (cnt&1)==1 ){
                System.out.println(Thread.currentThread().getName()+"  奇数: "+ cnt);
                cnt++;
            }
            if ( type ==0 && (cnt&1)==0 ){
                System.out.println(Thread.currentThread().getName()+"  偶数: "+ cnt);
                cnt++;
            }
        }
    }
}
