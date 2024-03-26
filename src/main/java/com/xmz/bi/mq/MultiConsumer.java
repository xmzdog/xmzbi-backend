package com.xmz.bi.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class MultiConsumer {

  private static final String TASK_QUEUE_NAME = "task_queue";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("192.168.10.137");
    final Connection connection = factory.newConnection();
    final Channel channel = connection.createChannel();

    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    channel.basicQos(1);

    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), "UTF-8");

        try {
//            doWork(message);
            // 处理工作
            System.out.println(" [x] Received '" + message + "'");
            // 停 20s 模拟机器处理能力有限
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(" [x] Done");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    };
    // 开启消费监听
    channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> { });
  }

//  private static void doWork(String task) {
//    for (char ch : task.toCharArray()) {
//        if (ch == '.') {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException _ignored) {
//                Thread.currentThread().interrupt();
//            }
//        }
//    }
//  }
}