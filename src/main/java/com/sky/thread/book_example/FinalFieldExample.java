package com.sky.thread.book_example;

public class FinalFieldExample {
    final int x;
    int y;
    static FinalFieldExample instance;

    public FinalFieldExample() {
        x = 1;
        y = 2;
    }

    public static void writer() {
        instance = new FinalFieldExample();
    }

    public static void finalReader() {
        final FinalFieldExample theInstance = instance;
        if (theInstance != null) {
            System.out.println("x = "+ theInstance.x);
            System.out.println("y = " + theInstance.y);
        }else {
            System.out.println("theInstance 为空");
        }
    }

    public static void reader() {
        if (instance != null) {
            System.out.println("x = "+ instance.x);
            System.out.println("y = " + instance.y);
        }else {
            System.out.println("instance 为空");
        }
    }


    public static void main(String[] args) {
        FinalFieldExample.writer();
        FinalFieldExample.reader();
    }
}
