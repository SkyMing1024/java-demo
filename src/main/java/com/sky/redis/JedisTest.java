package com.sky.redis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.codemodel.internal.JForEach;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class JedisTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("139.196.30.90",6379);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","sky");
        jsonObject.put("age",18);
        String str = jsonObject.toJSONString();

//        jedis.set("user",str);

        Set<String> result = jedis.keys("*");
        System.out.println(result);
        for (String s : result) {
            String value = jedis.get(s);
            System.out.println(s+" : "+value);
        }



    }


}
