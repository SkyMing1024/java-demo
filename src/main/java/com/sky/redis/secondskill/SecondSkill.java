package com.sky.redis.secondskill;

import com.sky.redis.RedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * 模拟秒杀
 */
public class SecondSkill implements Runnable{

    final String iPhone = "iPhone";
    Jedis jedis = RedisUtil.getJedis();
    String userInfo;

    public SecondSkill() {
    }

    public SecondSkill(String userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public void run() {
        try {


            jedis.watch(iPhone);
        String val = jedis.get(iPhone);
        int valint = Integer.valueOf(val);
        if (valint<=100 && valint>=1){
            // 1.开启事务
           Transaction tx = jedis.multi();

           // 2.命令入队
            tx.incrBy("iPhone",-1);

            // 3.执行事务
            // 提交事务，如果此时watchkeys被改动了，则返回null
            List<Object> list = tx.exec();
            if (list == null || list.size() == 0){
                String failUserInfo = "fail" + userInfo;
                String failInfo = "用户："+failUserInfo + "商品争抢失败，抢购失败";
                System.out.println(failInfo);
                // 抢购失败逻辑
                jedis.setnx(failUserInfo,failInfo);
            }else {
                for(Object succ : list){
                    String succUserInfo = "succ"+succ.toString()+userInfo;
                    String succInfo = "用户："+succUserInfo + "抢购成功，当前成功人数：" + (1-(valint-100));
                    System.out.println(succInfo);
                    // 抢购成功逻辑
                    jedis.setnx(succUserInfo,succInfo);
                }
            }
        }else {
            String failUserInfo = "fail" + userInfo;
            String failInfo = "用户："+failUserInfo + "商品已售罄，抢购失败";
            System.out.println(failInfo);
            // 抢购失败逻辑
            jedis.setnx(failUserInfo,failInfo);
//                Thread.sleep(20);

            return;
        }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            jedis.close();
        }
    }
}
