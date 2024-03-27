package com.xmz.bi.mq;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author xmz
 * @date 2024-03-27
 */
@Component
@Slf4j
public class MyMessageConsumer {


    @SneakyThrows
    @RabbitListener(queues = {"BI"},ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliverTag){
        log.info("receiveMessage : " + message);
        channel.basicAck(deliverTag,false);
    }
}
