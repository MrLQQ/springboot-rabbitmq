package com.mrlqq.rabbitmq.springbootrabbitmq.consumer;

import com.mrlqq.rabbitmq.springbootrabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Classname DelayQueueConsumer
 * @Description 消费者 基于插件的延迟消息
 * @Date 2022/1/5 16:44
 * @Created by LQQ
 */
@Slf4j
@Component
public class DelayQueueConsumer {

    // 监听消息
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayQueue(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间：{}，收到延迟队列的消息：{}",new Date().toString(),msg);
    }
}
