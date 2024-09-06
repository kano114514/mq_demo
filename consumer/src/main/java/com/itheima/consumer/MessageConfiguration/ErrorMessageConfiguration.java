package com.itheima.consumer.MessageConfiguration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuhaoyu
 * @date 2024/8/11  下午3:19
 * @description:用来将重试多次失败的消息发送到专门用来保存失败消息的队列中
 */
@Configuration
@RequiredArgsConstructor
public class ErrorMessageConfiguration {
    final RabbitTemplate rabbitTemplate;


    @Bean
    public DirectExchange errorExchange() {
        return new DirectExchange("error.direct");
    }

    @Bean
    public Queue errorQueue() {
        return new Queue("error.queue");
    }

    @Bean
    public Binding bindingError(Queue errorQueue, DirectExchange errorExchange) {
        return BindingBuilder.bind(errorQueue).to(errorExchange).with("error");
    }

    @Bean
    public MessageRecoverer meesageRecoverer(){
        return new RepublishMessageRecoverer(rabbitTemplate,"error.direct","error");
    }
}