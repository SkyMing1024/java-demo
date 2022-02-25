package com.sky.rabbitmq.route;

import com.rabbitmq.client.*;
import com.sky.rabbitmq.ConnectionUtil;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RouteReciver2 {
    private final static String EXCHANGE_NAME = "test_route_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        String queue2 = "test_route_queue2";// 队列名称
        channel.queueDeclare(queue2, false, false, false, null);

        // 绑定队列到交换机
        // 指定routingKey，只有同样标记的消息才会被当前消费者接收到
        channel.queueBind(queue2, EXCHANGE_NAME, "key3");
        // 可以绑定多个key
        channel.queueBind(queue2, EXCHANGE_NAME, "key4");
        channel.basicQos(1);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2收到消息：" + new String(body) + "   交换器：" + envelope.getExchange() + "    关键字：" + envelope.getRoutingKey());
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(queue2, false, defaultConsumer);

    }
}
