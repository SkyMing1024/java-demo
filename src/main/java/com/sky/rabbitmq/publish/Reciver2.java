package com.sky.rabbitmq.publish;

import com.rabbitmq.client.*;
import com.sky.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Reciver2 {
    private final static String EXCHANGE_NAME = "testexchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare("testqueue2", false, false, false, null);

        // 绑定队列到交换机
        channel.queueBind("testqueue2", EXCHANGE_NAME, "");
        channel.basicQos(1);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume("testqueue2", false, defaultConsumer);

    }
}
