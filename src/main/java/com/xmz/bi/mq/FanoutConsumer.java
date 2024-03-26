package com.xmz.bi.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class FanoutConsumer {

    private static final String EXCHANGE_NAME = "fanout-exchange";

    public static void main(String[] argv) throws Exception {
        // 建立连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.10.137");
        final Connection connection = factory.newConnection();
        // 创建管道
        final Channel channel1 = connection.createChannel();
        final Channel channel2 = connection.createChannel();
        // 声明交换机
        channel1.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 创建队列，随机分配一个队列名称
        String queueName1 = "xiaoming_queue";
        channel1.queueDeclare(queueName1,true,false,false,null);
        channel1.queueBind(queueName1,EXCHANGE_NAME,"");

        String queueName2 = "zhangsan_queue";
        channel1.queueDeclare(queueName2,true,false,false,null);
        channel1.queueBind(queueName2,EXCHANGE_NAME,"");


        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback1 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");

        };
        DeliverCallback deliverCallback2 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");

        };
        // 开启消费监听
        channel1.basicConsume(queueName1, false, deliverCallback1, consumerTag -> {});
        channel1.basicConsume(queueName2, false, deliverCallback2, consumerTag -> {});
    }

}