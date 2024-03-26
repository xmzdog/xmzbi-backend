package com.xmz.bi.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.10.137");
//        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("xmz123");
        // 建立连接，创建频道
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 创建消息队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //发送消息
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + message + "'");

    }
}