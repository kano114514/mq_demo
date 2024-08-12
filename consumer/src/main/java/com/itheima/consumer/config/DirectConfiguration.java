package com.itheima.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//
@Configuration
public class DirectConfiguration {
//    @Bean
//    public DirectExchange directExchange(){
////        return new FanoutExchange("hmall.fanout");
////
//        return ExchangeBuilder.directExchange("hmall.direct").build();
//    }
//
//
//    @Bean
//    public Queue directQueue1(){
////        return new Queue("fanout.queue1");
//        return QueueBuilder.durable("direct.queue1").build();
//    }
//
//    @Bean
//    public Binding directQueue1Binding(Queue directQueue1,DirectExchange directExchange){
//        return BindingBuilder.bind(directQueue1).to(directExchange).with("red");
//    }
//
//    @Bean
//    public Queue fanoutQueue2(){
////        return new Queue("fanout.queue1");
//        return QueueBuilder.durable("fanout.queue2").build();
//    }
//
//    @Bean
//    public Binding fanoutQueue2Binding(Queue fanoutQueue2,FanoutExchange fanoutExchange){
//        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
//    }

    @Bean
    public DirectExchange normalExchange(){
        return new DirectExchange("normal.direct");
    }
    @Bean
    public Queue normalQueue(){
        return QueueBuilder.durable("normal.queue").deadLetterExchange("dlx.direct").build();
    }
    @Bean
    public Binding normalQueueBinding(Queue normalQueue,DirectExchange normalExchange){
        return BindingBuilder.bind(normalQueue).to(normalExchange).with("blue");
    }
}
