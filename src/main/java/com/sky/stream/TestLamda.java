package com.sky.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestLamda {

    private static List<Apple> appleStore = new ArrayList<>();

    static {
        appleStore.add(new Apple(1,"red",300,"北京"));
        appleStore.add(new Apple(2,"red",500,"南京"));
        appleStore.add(new Apple(3,"green",500,"南京"));
        appleStore.add(new Apple(4,"green",300,"陕西"));
        appleStore.add(new Apple(5,"green",500,"陕西"));
        appleStore.add(new Apple(6,"red",300,"陕西"));
        appleStore.add(new Apple(7,"yellow",300,"南京"));
        appleStore.add(new Apple(8,"yellow",400,"北京"));
    }


    // 找出红色苹果
    public void test1(){
        for (Apple apple : appleStore) {
            if ("red".equals(apple.getColor())){
                System.out.println(apple);

            }
        }
    }

    // 找出红色苹果
    public void test2(){
        List<Apple> redApples = appleStore.stream()
                .filter(apple -> apple.getColor().equals("red"))
                .collect(Collectors.toList());
        
        System.out.println(redApples);

    }

    // 过滤条件从外部传入
    public void test3(Predicate<? super Apple> pr){
        List<Apple> apples = appleStore.stream()
                .filter(pr)
                .collect(Collectors.toList());
        
        System.out.println(apples);
    }

    // 每个颜色的平均重量
    public void test4(){
        // 按颜色分组
        Map<String, List<Apple>> maps = new HashMap<>();
        for (Apple apple : appleStore){
            List<Apple> list = maps.computeIfAbsent(apple.getColor(), a -> new ArrayList<>());
            list.add(apple);
        }
        // 按颜色求平均
        for (Map.Entry<String, List<Apple>> entry : maps.entrySet()) {
            System.out.println(entry);
            int sum = 0;
            for(Apple apple : entry.getValue()){
                sum += apple.getWeight();
            }
            System.out.println(String.format("颜色%s 平均重量%s",entry.getKey(), sum / entry.getValue().size()));
        }
    }


    // 每个颜色的平均重量
    public void test5(){
        appleStore.stream()
                .collect(Collectors.groupingBy(apple -> apple.getColor(),// 重量分组
                        Collectors.averagingInt(a -> a.getWeight()))) // 求平均
                .forEach((k,v)->System.out.println(String.format("颜色%s : %s", k,v)));  // 输出
    }

    // 基于lamda实现test5
    public static void main(String[] args) {
//        Predicate<? super Apple> pr = a->"red".equals(a.getColor())&&"北京".equals(a);
//        new TestStream().test3(a->"red".equals(a.getColor())&&"北京".equals(a.getPlace()));
        new TestLamda().test5();
    }

}
