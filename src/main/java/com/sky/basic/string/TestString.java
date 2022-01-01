package com.sky.basic.string;

public class TestString {

    public static final String a = "123";
    public static final String b = "456";

    public static void main(String[] args) {
        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = "Hel" + "lo";
        String s4 = "Hel" + new String("lo");
        String s5 = new String("Hello");
        String s6 = s5.intern();
        String s7 = "Hel";
        String s8 = "lo";
        String s9 = s7 + s8;
        String s10 = new String("H") + new String("ello");

        System.out.println("s1 == s2：" + (s1==s2));  // true
        System.out.println("s1 == s3："+ (s1 == s3));  // true
        System.out.println("s1 == s4："+ (s1 == s4));  // false
        System.out.println("s1 == s9："+ (s1 == s9));  // false
        System.out.println("s1 == s5："+ (s1 == s5));  // false
        System.out.println("s4 == s5："+ (s4 == s5));  // false
        System.out.println("s4 == s9："+ (s4 == s9));  // false
        System.out.println("s1 == s6："+ (s1 == s6));  // true
        System.out.println("s1 == s10："+ (s1 == s10));  // false


//        String c = "123456";
//        String d = a + b;
//        System.out.println(c == d);
//
//
//        Integer i1 = 40;
//        Integer i2 = 40;
//        Integer i3 = 0;
//        Integer i4 = new Integer(40);
//        Integer i5 = new Integer(40);
//        Integer i6 = new Integer(0);
//
//        System.out.println("i1=i2   " + (i1 == i2));
//        System.out.println("i1=i2+i3   " + (i1 == i2 + i3));
//        System.out.println("i1=i4   " + (i1 == i4));
//        System.out.println("i4=i5   " + (i4 == i5));
//        System.out.println("i4=i5+i6   " + (i4 == i5 + i6));
//        System.out.println("40=i5+i6   " + (40 == i5 + i6));

    }
}
