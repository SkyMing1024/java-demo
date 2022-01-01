package com.sky.reference;

import java.lang.ref.SoftReference;

/*
Java引用，
四种引用，强软弱虚
 */
public class TestReference {
    public static void main(String[] args) {

        SoftReference sr = new SoftReference(args);
    }
}
