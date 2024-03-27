package com.xmz.bi.bimq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xmz.bi.constant.BiMqConstant;

/**
 * @author xmz
 * @date 2024-03-27
 */
public class BIInitMain {

    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(BiMqConstant.BI_MQ_HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(BiMqConstant.BI_EXCHANGE, "direct");

            channel.queueDeclare(BiMqConstant.BI_QUEUES, true, false, false, null);
            channel.queueBind(BiMqConstant.BI_QUEUES, BiMqConstant.BI_EXCHANGE, BiMqConstant.BI_ROUTINGKEY);
        } catch (Exception e) {

        }
    }
}
