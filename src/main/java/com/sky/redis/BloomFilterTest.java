package com.sky.redis;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class BloomFilterTest {
    public static void main(String[] args) {
        int total = 1000000;
        BloomFilter bloomFilter = BloomFilter.create(Funnels.integerFunnel(),total,0.01);

        for (int i = 0; i < total; i++) {
            bloomFilter.put(i);

        }

        int count = 0;
        for (int i = total; i < total*1.1; i++) {

            if (bloomFilter.mightContain(i)){
                count++;
                System.out.println(i+" 误判");
            }

        }
        System.out.println("总共误判："+count);
        double rate = count/total;
        System.out.println("误判率："+ rate);




    }



}
