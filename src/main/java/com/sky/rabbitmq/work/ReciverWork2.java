package com.sky.rabbitmq.work;

import com.rabbitmq.client.*;
import com.sky.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReciverWork2 {
    private final static String QUEUE = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        // 队列名称  是否持久化队列   是否排位（连接关闭后是否自动删除队列）
        channel.queueDeclare(QUEUE, false, false, false, null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者2收到消息：" + new String(body));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 确认收到
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 通知服务器，已收到消息
        channel.basicConsume(QUEUE, false, defaultConsumer);
    }
}
