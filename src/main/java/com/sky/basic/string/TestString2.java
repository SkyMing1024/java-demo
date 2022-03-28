package com.sky.basic.string;

public class TestString2 {
    public static void main(String []args) {
        String str1 = "abc";
        String str2 = new String("def");
        String str3 = "abc";
        String str4 = str2.intern();
        String str5 = "def";
//        System.out.println(str1 == str3);//true
//        System.out.println(str2 == str4);//false
//        System.out.println(str2 == str5);//false
//        System.out.println(str4 == str5);//true

        String t1 = new String("skyming");
        String t2 = t1.intern();
        String t3 = t2.intern();
        String t4 = "skyming";
        System.out.println(t1 == t2);
        System.out.println(t1 == t3);
        System.out.println(t1 == t4);
        System.out.println(t2 == t4);
        System.out.println(t3 == t4);
        System.out.println(t3 == t2);


    }
}
