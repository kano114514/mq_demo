package com.itheima.consumer.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NewTestRabbitListener {
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "today.direct.queue",durable = "true"),
            exchange = @Exchange(name = "today.direct.exchange",type = ExchangeTypes.DIRECT),
            key = {"peace","forward"}
    ))
    public void listen(String message){
        System.out.println(message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "today.topic.queue",durable = "true"),
            exchange = @Exchange(name = "today.topic.exchange",type = ExchangeTypes.TOPIC),
            key = {"*.peace.*"}
    ))
    public void listenTopic(String message){
        System.out.println("topic"+message);
    }
}
