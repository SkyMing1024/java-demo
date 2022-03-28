package com.sky.redis;

import redis.clients.jedis.Jedis;

public class RedisUtil {

    public synchronized static Jedis getJedis(){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        return jedis;
    }
}
