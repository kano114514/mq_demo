package com.itheima.consumer.listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class SpringRabbitListener {
//
//    @RabbitListener(queues = "simple.queue")
//    public void simpleListener(String message){
//        System.out.println(message);
//        log.info("监听到simple.queue的消息 【{}】",message);
//    }
//
//
//    @RabbitListener(queues = "work.queue")
//    public void workListener1(String message) throws InterruptedException{
//        Thread.sleep(25);
//        System.out.println("消费者1接收到消息"+message+"!");
//    }
//    @RabbitListener(queues = "work.queue")
//    public void workListener2(String message) throws InterruptedException{
//        Thread.sleep(200);
//        System.err.println("消费者2接收到消息"+message+"!");
//    }
//
//    @RabbitListener(queues = "fanout.queue1")
//    public void fanoutListener1(String message){
//        System.out.println("消费者1接收到消息"+message);
//    }
//    @RabbitListener(queues = "fanout.queue2")
//    public void fanoutListener2(String message){
//        System.out.println("消费者2接收到消息"+message);
//    }
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = "direct.queue1",durable = "true"),
//            exchange = @Exchange(name="hmall.direct",type = ExchangeTypes.DIRECT),
//            key = {"red","blue"}
//    ))
//    public void directListener1(String message){
//        System.out.println("消费者1接收消息"+message);
//    }
//
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = "direct.queue2",durable = "true"),
//            exchange = @Exchange(name="hmall.direct",type = ExchangeTypes.DIRECT),
//            key = {"red","yellow"}
//    ))
//    public void directListener2(String message){
//        System.out.println("消费者2接收消息"+message);
//    }
//
//    @RabbitListener(queues="direct.queue1")
//    public void directListener3(Map message){
//        System.out.println("消费者1接受信息"+message.toString());
//    }
//
//    @RabbitListener(queues="topic.queue1")
//    public void topicListener1(String message){
//        System.out.println("消费者1接受信息"+message);
//    }
//
//    @RabbitListener(queues="topic.queue2")
//    public void topicListener2(String message){
//        System.out.println("消费者2接受信息"+message);
//    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "lazy.queue",durable="true",arguments = @Argument(name="x-queue-mode", value = "lazy")),
            exchange = @Exchange(name="simple.direct",type = ExchangeTypes.DIRECT),
            key = {"blue"}
    ))
    public void simpleListener2(String message){
        System.out.println(message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "simple.queue",durable="true"),
            exchange = @Exchange(name="simple.direct",type = ExchangeTypes.DIRECT),
            key = {"blue"}
    ))
    public void simpleListener1(Message message){
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties().getMessageId()) ;
        throw new RuntimeException("消息转换异常");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name="dlx.queue",durable = "true"),
            exchange = @Exchange(name = "dlx.direct",type = ExchangeTypes.DIRECT),
            key = {"blue"}
    ))
    public void dlxListener(String message){
        System.out.println("死信消息收到:"+message);
    }
}
