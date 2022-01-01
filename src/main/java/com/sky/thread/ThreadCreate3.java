package com.sky.thread;



public class ThreadCreate3 {

    public static void main(String[] args) {

        /**
         * 实现Runnable接口方式创建线程，匿名内部类写法
         */
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("hello");
                    }
                }
        ).start();

        /**
         * 实现Runnable接口方式创建线程，lamda表达式 写法
         */
        new Thread(
                () -> System.out.println("hello")
        ).start();


        Runnable runnable = () -> System.out.println();

        Runnable runnable1 = () -> {
            System.out.println();
        };



    }
}
