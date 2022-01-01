package com.sky.thread;

import org.openjdk.jol.info.ClassLayout;

public class JolTest {

    public static void main(String[] args) {
        Object obj = new Object();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }
}
