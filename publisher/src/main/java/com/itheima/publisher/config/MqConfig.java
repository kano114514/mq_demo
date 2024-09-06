package com.itheima.publisher.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@AllArgsConstructor
@Configuration
public class MqConfig {
    private final RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){

        //return时进行回调的确定，
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback(){

            @Override
            public void returnedMessage(ReturnedMessage returned) {
                log.error("触发 return callback");
                log.debug("交换机:{}",returned.getExchange());
                log.debug("路由键:{}",returned.getRoutingKey());
                log.debug("消息:{}",returned.getMessage());
                log.debug("异常代码:{}",returned.getReplyCode());
                log.debug("异常信息:{}",returned.getReplyText());
                System.out.println("收到了return报告。");
            }
        });
    }
}
