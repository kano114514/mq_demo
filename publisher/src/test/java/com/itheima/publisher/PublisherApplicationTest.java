package com.itheima.publisher;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFutureCallback;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class PublisherApplicationTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    public void testAMQP(){
        CorrelationData cd=new CorrelationData();
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
        String queueName="simple.queue";

        String message="hello,Spring amqp";

        rabbitTemplate.convertAndSend(queueName,message);
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
        String exchange="direct.queue1";
        Map<String, Object> message=new HashMap<>();
        message.put("name","jack");
        message.put("age",21);
        rabbitTemplate.convertAndSend(exchange,message);
    }


    @Test
    public void testTopic(){
        String exchange="hmall.topic";
        String message="xjpsmshxta";
        rabbitTemplate.convertAndSend(exchange,"china",message);
    }

}