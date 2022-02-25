package com.sky.rabbitmq.route;

import com.rabbitmq.client.*;
import com.sky.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RouteReciver1 {
    private final static String EXCHANGE_NAME = "test_route_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        String queue1 = "test_route_queue1"; // 队列名称
        channel.queueDeclare(queue1, false, false, false, null);

        // 绑定队列到交换机
        // 指定routingKey，只有同样标记的消息才会被当前消费者接收到
        channel.queueBind(queue1, EXCHANGE_NAME, "key1");
        // 可以绑定多个key
        channel.queueBind(queue1, EXCHANGE_NAME, "key2");
        channel.basicQos(1);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1收到消息：" + new String(body) + "   交换器：" + envelope.getExchange() + "    关键字：" + envelope.getRoutingKey());
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(queue1, false, defaultConsumer);

    }
}
