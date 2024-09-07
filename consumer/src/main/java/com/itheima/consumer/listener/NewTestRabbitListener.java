package com.itheima.consumer.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.converter.MessageConversionException;
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

//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = "today.topic.queue",durable = "true"),
//            exchange = @Exchange(name = "today.topic.exchange",type = ExchangeTypes.TOPIC),
//            key = {"*.peace.*"}
//    ))
//    public void listenTopic(String message){
//        System.out.println("topic"+message);
//    }

    @RabbitListener(queues = "today.topic.queue")
    public void throwSomeError(String message){
        throw new MessageConversionException("fuck");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "dlx_queue",durable = "true"),
            exchange = @Exchange(name = "dlx_exchange",type = ExchangeTypes.DIRECT),
            key = {"dlx"}
    ))
    public void dlxL(String message){
        System.out.println("处理到死信了");
    }
}
