package com.sky.redis.secondskill;

import com.sky.redis.RedisUtil;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SecondKillTest {
    public static void main(String[] args) {
        final String iPhone = "iPhone";
        ExecutorService executor = Executors.newFixedThreadPool(20);

        final Jedis jedis = RedisUtil.getJedis();
        jedis.del(iPhone);
        // 起始库存
        jedis.set(iPhone,"100");

        jedis.close();
        for (int i = 0; i < 1000; i++) {
            executor.execute(new SecondSkill("user"+i));
        }
        executor.shutdown();

    }
}
