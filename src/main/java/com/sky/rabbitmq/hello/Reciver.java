package com.sky.rabbitmq.hello;

import com.rabbitmq.client.*;
import com.sky.rabbitmq.ConnectionUtil;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Reciver {
    private final static String QUEUE = "Test_Queue";

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {

        // 获取连接
        Connection connection = ConnectionUtil.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        // 队列名称  是否持久化队列   是否排位（连接关闭后是否自动删除队列）
        channel.queueDeclare(QUEUE, false, false, false, null);
        // 接收消息
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者收到消息：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE,false,consumer);



        // 关闭连接
//        connection.close();
    }
}
