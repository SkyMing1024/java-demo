package com.sky.rabbitmq.topic;

import com.rabbitmq.client.*;
import com.sky.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicReciver2 {
    private final static String EXCHANGE_NAME = "testtopic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare("testtopicqueue2", false, false, false, null);

        // 绑定队列到交换机
        // 指定routingKey，只有同样标记的消息才会被当前消费者接收到
        channel.queueBind("testtopicqueue2", EXCHANGE_NAME, "abc.*");
        // 可以绑定多个key
        channel.basicQos(1);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume("testtopicqueue2", false, defaultConsumer);
    }
}
