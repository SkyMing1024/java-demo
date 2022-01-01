package com.sky.thread;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 《深入理解JVM》代码13-2
 */
public class TestThreadSafe {

    private static Vector<Integer> vector = new Vector<Integer>();

    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }

            Thread removeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);
                    }
                }
            });


            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        System.out.println((vector.get(i)));
                    }
                }
            });

            removeThread.start();
            printThread.start();


            //不要同时产生过多的线程，否则会导致操作系统假死
            while (Thread.activeCount() > 20);
        }

    }

}
