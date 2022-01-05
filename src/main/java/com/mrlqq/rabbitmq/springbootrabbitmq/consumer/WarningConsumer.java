package com.mrlqq.rabbitmq.springbootrabbitmq.consumer;

import com.mrlqq.rabbitmq.springbootrabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Classname WarningConsumer
 * @Description 报警消费者
 * @Date 2022/1/5 22:33
 * @Created by LQQ
 */
@Slf4j
@Component
public class WarningConsumer {

    // 接收报警消息
    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
    public void receivedWarningMsg(Message message){
        String msg = new String(message.getBody());
        log.error("报警发现不可用路由信息：{}",msg);
    }
}
