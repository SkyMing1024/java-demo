package com.sky.rabbitmq.route;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sky.rabbitmq.ConnectionUtil;
import org.springframework.amqp.core.ExchangeTypes;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RouteSender {

    private final static String EXCHANGE_NAME = "test_route_exchange";


    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 定义路由模式的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.DIRECT);
        // 发送消息
        channel.basicPublish(EXCHANGE_NAME, "key3", null, "路由模式发送的消息".getBytes());

        channel.close();
        connection.close();

    }
}
