package com.xmz.bi.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class DirectConsumer {

    private static final String EXCHANGE_NAME = "direct-exchange";

    public static void main(String[] argv) throws Exception {
        // 建立连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.10.137");
        final Connection connection = factory.newConnection();
        // 创建管道
        final Channel channel = connection.createChannel();
        // 声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        // 创建队列，随机分配一个队列名称
        String queueName1 = "xiang_queue";
        channel.queueDeclare(queueName1,true,false,false,null);
        channel.queueBind(queueName1,EXCHANGE_NAME,"xiang");

        String queueName2 = "gong_queue";
        channel.queueDeclare(queueName2,true,false,false,null);
        channel.queueBind(queueName2,EXCHANGE_NAME,"gong");


        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback1 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "' routingKey:" + delivery.getEnvelope().getRoutingKey() );

        };
        DeliverCallback deliverCallback2 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "' routingKey:" + delivery.getEnvelope().getRoutingKey());

        };
        // 开启消费监听
        channel.basicConsume(queueName1, false, deliverCallback1, consumerTag -> {});
        channel.basicConsume(queueName2, false, deliverCallback2, consumerTag -> {});
    }

}