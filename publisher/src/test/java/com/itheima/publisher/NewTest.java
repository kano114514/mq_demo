package com.itheima.publisher;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFutureCallback;

@SpringBootTest
@Slf4j
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


    @Test
    public void testPublisherConfirm(){
        CorrelationData cd=new CorrelationData();//cd其实可以认为是一个异步操作的封装，其存了一个Future用来保存confirm返回时的结果。
        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("handle message ack file",ex);//onFailure指的是处理confirm时出现的异常，很少触发
            }

            @Override
            public void onSuccess(CorrelationData.Confirm result) {
                if(result.isAck()){
                    log.debug("ok!");
                    System.out.println("ok");
                }
                else {
                    log.error("发送消息失败，收到nack，reason:{}",result.getReason());
                    System.out.println("发送消息失败，收到nack");
                }
            }
        });  //addCallback()就是在这个future里的结果返回的时候增加一个回调函数。
        String message="testReturn";
        rabbitTemplate.convertAndSend("today.topic.exchange","nack.h",message,cd);
    }
}
