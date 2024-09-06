package com.itheima.publisher;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NewTest {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void sendDirectMessage(){
        String message="directMessage";
        String exchange="today.direct.exchange";
        rabbitTemplate.convertAndSend(exchange,"peace",message);
    }

    @Test
    public void sendTopicMessage(){
        String message="topicMessage";
        String exchange="today.topic.exchange";
        rabbitTemplate.convertAndSend(exchange,"make.peace.h",message);
    }
}
