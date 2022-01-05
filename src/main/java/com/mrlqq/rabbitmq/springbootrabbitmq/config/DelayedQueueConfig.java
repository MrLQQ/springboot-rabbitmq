package com.mrlqq.rabbitmq.springbootrabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname DelayedQueueConfig
 * @Description TODO
 * @Date 2022/1/5 16:18
 * @Created by LQQ
 */
@Configuration
public class DelayedQueueConfig {

    // 交换机
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    // 队列
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    // routingKey
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";

    // 声明队列
    @Bean
    public Queue delayedQueue(){
        return new Queue(DELAYED_QUEUE_NAME);
    }

    // 声明交换机 基于插件的
    @Bean
    public CustomExchange delayedExchange(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type","direct");

        /**
         * 1.交换机的名字
         * 2.交换机的类型
         * 3.是否需要持久化
         * 4.是否需要自动删除
         * 5.其他参数
         */
        return new CustomExchange(DELAYED_EXCHANGE_NAME,"x-delayed-message",
                true,false,arguments);
    }

    // 绑定
    @Bean
    public Binding delayedQueueBindingDelayedExchange(@Qualifier("delayedQueue") Queue delayedQueue,
                                                      @Qualifier("delayedExchange") CustomExchange delayedExchange){
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }

}
