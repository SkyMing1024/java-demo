package com.sky.stream;

import com.sky.App;
import javafx.application.Application;

import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestStream {

    private static List<Apple> appleStore = new ArrayList<>();

    static {
        appleStore.add(new Apple(1,"red",300,"北京"));
        appleStore.add(new Apple(2,"red",500,"南京"));
        appleStore.add(new Apple(3,"green",500,"南京"));
//        appleStore.add(new Apple(4,"green",300,"陕西"));
//        appleStore.add(new Apple(5,"green",500,"陕西"));
//        appleStore.add(new Apple(6,"red",300,"陕西"));
//        appleStore.add(new Apple(7,"yellow",300,"南京"));
//        appleStore.add(new Apple(8,"yellow",400,"北京"));
    }

    public static void main(String[] args) {
        // list->stream
        // 生成流的方法
        Stream<Apple> stream1 = appleStore.stream();
//        IntStream stream2 = Arrays.stream(new int[]{1, 2, 3});
//        Stream<? extends Serializable> stream3 = Stream.of(1, 2, 3, 4, 'a');

        Stream<Apple> newStream1 = stream1.filter(apple -> apple.getColor().equals(""));


//        appleStore.stream()
//                .filter(apple -> "red".equals(apple.getColor()) || "green".equals(apple.getColor()))
//                .map(apple -> apple.getColor())
//                .distinct()
//                .peek(apple -> System.out.println(apple))
//                .toArray();

//        Map<String, List<Apple>> groupByPlace = appleStore.stream().collect(Collectors.groupingBy(Apple::getPlace));
//
//        Set<String> keySet = groupByPlace.keySet();
//
//        keySet.stream().forEach(place->{
//            List<Apple> apples = groupByPlace.get(place);
//        });

        List<Apple> list = null;
        list.stream().forEach(item->{

        });


        System.out.println(appleStore);

    }

}
