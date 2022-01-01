package com.sky.redis;

import redis.clients.jedis.Jedis;

public class RedisUtil {

    public synchronized static Jedis getJedis(){
        Jedis jedis = new Jedis("139.196.30.90",6379);
        return jedis;
    }
}
