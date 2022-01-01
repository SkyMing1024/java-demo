package com.sky;

import org.omg.PortableInterceptor.INACTIVE;
import org.openjdk.jol.info.ClassLayout;

public class Main {

    public static void main(String[] args) {

        int a1 = 100;
        Integer a2 = 100;
        Integer a3 = 100;
        Integer a4 = new Integer(100);;
        Integer a5 = Integer.valueOf(100);

        System.out.println("a1 == a4:"+ (a1 == a4));
        System.out.println("a2 == a4:"+ (a2 == a4));
        System.out.println("a3 == a4:"+ (a3 == a4));
        System.out.println("a1 == a5:"+ (a1 == a5));



        int b1 = 200;
        Integer b2 = 200;
        Integer b3 = new Integer(200);
        Integer b4 = new Integer(200);;
        Integer b5 = Integer.valueOf(200);

        System.out.println("b1 == b4:"+ (b1 == b4));
        System.out.println("b2 == b4:"+ (b2 == b4));
        System.out.println("b3 == b4:"+ (b3 == b4));
        System.out.println("b1 == b5:"+ (b1 == b5));

        System.out.println(ClassLayout.parseInstance(b2).toPrintable());
        System.out.println("******************************************************************************************************************************************************************");
        System.out.println(ClassLayout.parseClass(b5.getClass()).toPrintable());

    }
    
}
