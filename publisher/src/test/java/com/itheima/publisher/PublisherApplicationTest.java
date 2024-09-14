package com.itheima.publisher;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFutureCallback;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class PublisherApplicationTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    void testSendNonePersistentMessage() throws InterruptedException {
        String ququename="normal.queue";
        //1. 自定义构建消息就能测试非持久化了
//        Message build = MessageBuilder.withBody("hello,World".getBytes(StandardCharsets.UTF_8))
//                .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT).build();
        String message="hello";
        for(int i=0;i<10;i++){

            rabbitTemplate.convertAndSend(ququename,message);
        }
    }
    @Test
    public void testAMQP() throws InterruptedException {
        UUID uuid = UUID.randomUUID();
        CorrelationData cd=new CorrelationData(uuid.toString());
        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("handle message",ex);
            }
            @Override
            public void onSuccess(CorrelationData.Confirm result) {
                if(result.isAck()){
                    log.debug("发送信息成功,收到ack");
                }
                else{
                    log.error("发送信息失败，收到nack reason:{}",result.getReason());
                }
            }
        });
        String exchangeName="simple.direct";
        String queueName="simple.direct.queue";
        String routingKey="fuck";
        String message="hello,Spring amqp";

        rabbitTemplate.convertAndSend(exchangeName,routingKey,message,cd);
        Thread.sleep(2000);
    }

    @Test
    public void testWorkAMQP(){
        String queueName="work.queue";
        for (int i=0;i<50;i++){
            String message="hello,Spring amqp"+i;
            rabbitTemplate.convertAndSend(queueName,message);
        }
    }
    @Test
    public void testWorkFanout(){
        String exchange="hmall.fanout";
        String message="hello,fanout";
        rabbitTemplate.convertAndSend(exchange,null,message);
    }

    @Test
    public void testDirect(){
        String exchange="today.ttl.queue";
        String ttl="ttl";
        rabbitTemplate.convertAndSend(exchange,ttl);
    }


    @Test
    public void testTopic(){
        String exchange="hmall.topic";
        String message="xjpsmshxta";
        rabbitTemplate.convertAndSend(exchange,"china",message);
    }

    @Test
    void testSendDelayMessage() throws InterruptedException {
        rabbitTemplate.convertAndSend("normal.direct", "blue", "hello", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("5000");
                return message;
            }
        });
    }

}