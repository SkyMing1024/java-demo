package com.sky.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.sky.rabbitmq.ConnectionUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicSender implements ConfirmCallback {

    private final static String EXCHANGE_NAME = "testtopic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 定义主题模式的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        // 发送消息
        channel.basicPublish(EXCHANGE_NAME, "key1.1", null, "topic模式发来的消息，key: key1.1".getBytes());
        channel.basicPublish(EXCHANGE_NAME, "abc.abc", null, "topic模式发来的消息,key: abc.abc".getBytes());


        channel.close();
        connection.close();
    }


    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("消息回调："+ack);

    }

    @Override
    public void handle(long deliveryTag, boolean multiple) throws IOException {
        System.out.println("消息回调"+deliveryTag);
    }
}
