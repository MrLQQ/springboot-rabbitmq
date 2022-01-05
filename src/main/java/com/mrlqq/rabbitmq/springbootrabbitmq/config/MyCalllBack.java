package com.mrlqq.rabbitmq.springbootrabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Classname MyCalllBack
 * @Description 回调接口
 * @Date 2022/1/5 21:03
 * @Created by LQQ
 */
@Slf4j
@Component
public class MyCalllBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void  init(){
        // 注入
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     * 交换机确认回调方法
     * 1.发消息 交换机接收到了 回调
     * @param correlationData 保存回调消息的ID以及相关信息
     * @param ack 交换机收到消息 true
     * @param cause 失败原因 null
     *
     * 2.发消息 交换机接收失败了 回调
     *      * @param correlationData 保存回调消息的ID以及相关信息
     *      * @param ack 交换机收到消息 false
     *      * @param cause 失败原因
     */

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            log.info("交换机已经收到ID为：{}的消息",id);
        }else {
            log.info("交换机还未收到ID为：{}的消息，由于原因：{}",id,cause);
        }
    }

    /**
     * 可以在当消息传递过程中不可达目的地时将消息返回给生产者
     * @param returnedMessage
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("消息{}，被交换{}退回，退回的原因：{}，路由Key:{}",
                new String(returnedMessage.getMessage().getBody()),
                returnedMessage.getExchange(),
                returnedMessage.getReplyText(),
                returnedMessage.getRoutingKey());
    }

}
