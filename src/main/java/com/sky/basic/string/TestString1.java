package com.sky.basic.string;

import java.util.Collections;

public class TestString1 {

    public static void main(String[] args) {

        String s1 = "Hello";
        String s2 = "Hel" + "lo";
        String s3 = "Hel" + new String("lo");
        String s4 = "Hel";
        String s5 = "lo";
        String s6 = s4+s5;

        System.out.println("s1 == s2：" + (s1 == s2));
        System.out.println("s1 == s3："+ (s1 == s3));
        System.out.println("s1 == s6："+ (s1 == s6));


    }
}
