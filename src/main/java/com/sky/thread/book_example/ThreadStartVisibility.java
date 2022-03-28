package com.sky.thread.book_example;

import lombok.SneakyThrows;

/**
 * 线程启动与可见性
 * 参考《Java多线程编程实战指南》2-8
 *
 *
 */
public class ThreadStartVisibility {
    // 线程间的共享变量
    static int data = 0;

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                // 使当前线程休眠R毫秒（R的值为随机数）
                Thread.sleep(100);

                // 读取并打印变量data的值
                System.out.println(data);
            }
        };

        // 在子线程thread启动前更新变量data的值
        data = 1;// 语句①
        thread.start();

        // 使当前线程休眠R毫秒（R的值为随机数）
        Thread.sleep(50);

        // 在子线程thread启动后更新变量data的值
        data = 2;// 语句②

    }
}
