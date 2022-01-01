package com.sky;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        String a = "Hello World!";
        String a1 = "Hello";
        String a2 = "World";

        String b = new String("Hello World!");
        System.out.println(a == b);
        String c = b.intern();

        HashMap<String,Object> map = new HashMap<>(8);
        map.put("a",123);

        int aa = 65536;
        int bb = 1 >> 16;
        System.out.println(bb);

        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();

    }
}
