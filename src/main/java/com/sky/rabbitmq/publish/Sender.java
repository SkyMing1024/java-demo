package com.sky.rabbitmq.publish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sky.rabbitmq.ConnectionUtil;
import org.springframework.amqp.core.ExchangeTypes;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅模式需要先启动生产者（交换机）
 */

/**
 * 生产者
 */
public class Sender {
    private final static String EXCHANGE_NAME = "testexchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明交换机
        // 类型是fanout，即发布订阅模式
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.FANOUT);
        // 发送消息
        // 发布订阅模式，消息先发送到交换机，交换机没有保存功能，如果没有消费者，消息丢失
        channel.basicPublish(EXCHANGE_NAME, "", null, "发布订阅模式发布的消息".getBytes());
        channel.close();
        connection.close();
    }
}
