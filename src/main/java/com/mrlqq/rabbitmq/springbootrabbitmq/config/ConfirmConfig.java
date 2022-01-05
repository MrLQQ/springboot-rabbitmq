package com.mrlqq.rabbitmq.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.naming.directory.DirContext;

/**
 * @Classname ConfirmConfig
 * @Description 配置类 发布确认 （高级）
 * @Date 2022/1/5 20:36
 * @Created by LQQ
 */
@Configuration
public class ConfirmConfig {

    // 交换机
    public static final String CONFIRM_EXCHANGE_NAME = "confirm_exchange";
    // 队列
    public static final String CONFIRM_QUEUE_NAME = "confirm_queue";
    // RoutingKey
    public static final String CONFIRM_ROUTING_KEY = "key1";
    // 备份交换机
    public static final String BACKUP_EXCHANGE_NAME = "backup_exchange";
    // 备份队列
    public static final String BACKUP_QUEUE_NAME = "backup_queue";
    // 报警队列
    public static final String WARNING_QUEUE_NAME = "warning_queue";


    // 声明交换机
    @Bean("confirmExchange")
    public DirectExchange confirmExchange(){
        return ExchangeBuilder
                .directExchange(CONFIRM_EXCHANGE_NAME)
                .durable(true)
                .withArgument("alternate-exchange",BACKUP_EXCHANGE_NAME) //设置备份交换机
                .build();
    }

    // 声明队列
    @Bean("confirmQueue")
    public Queue confirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    // 绑定
    @Bean
    public Binding queueBindingExchange(@Qualifier("confirmExchange") DirectExchange confirmExchange,
                                        @Qualifier("confirmQueue") Queue confirmQueue){
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }

    // 声明备份交换机
    @Bean("backupExchange")
    public FanoutExchange backupExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    // 声明备份队列
    @Bean("backupQueue")
    public Queue backupQueue(){
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }

    // 声明报警队列
    @Bean("warningQueue")
    public Queue warningQueue(){
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    // 绑定备份交换机与备份队列
    @Bean
    public Binding queueBindingBackExchange(@Qualifier("backupExchange") FanoutExchange backupExchange,
                                            @Qualifier("backupQueue") Queue backupQueue){
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    // 绑定备份交换机与报警队列
    @Bean
    public Binding queueBindingWarningExchange(@Qualifier("backupExchange") FanoutExchange backupExchange,
                                            @Qualifier("warningQueue") Queue warningQueue){
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }
}
