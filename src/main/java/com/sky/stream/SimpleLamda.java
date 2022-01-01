package com.sky.stream;

import java.text.Format;
import java.text.SimpleDateFormat;

public class SimpleLamda {
    /**
     * 1.函数式接口
     * 2.参数传递
     * 3.代码编写方式
     * 4.方法引用
     * @param args
     */
    public static void main(String[] args) {

        run((name,age)->String.format("姓名：%s,年龄：%s",name,age));


        // 静态方法引用
        run(SimpleLamda::doFormat);
        // 普通方法引用
        run(new SimpleLamda()::doFormat2);


    }

    private String doFormat2(String name, int age) {
        return "name:"+name + " / "+"age："+ age;
    }

    private static String doFormat(String name, int age) {
        return "name:"+name + " / "+"age："+ age;
    }

    public static void run(Format Format){
        Format.run("sky",18);
    }

    public interface Format{
        String run(String name,int age);
    }

    /**
     * 函数式接口
     * 满足一下其中一点，便是函数式接口
     * 1.只有一个方法
     * 2.有多个方法，但是方法有默认实现
     * 3.多的方法是Object类的方法
     */
    @FunctionalInterface
    public interface MyRunable extends Runnable{
        public default void run2() {

        }
        String toString();
    }

}
