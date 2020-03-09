package com.training.config;

import com.training.dto.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列配置
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 行程消息实际消费队列所绑定的交换机
     */
    @Bean
    DirectExchange orderDirect() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_ROUTE_CANCEL.getExchange())
                .durable(true)
                .build();
    }

    /**
     * 行程延迟取消队列所绑定的交换机
     */
    @Bean
    DirectExchange orderTtlDirect() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_TTL_ROUTE_CANCEL.getExchange())
                .durable(true)
                .build();
    }

    /**
     * 行程实际消费队列
     */
    @Bean
    public Queue orderQueue() {
        return new Queue(QueueEnum.QUEUE_ROUTE_CANCEL.getName());
    }

    /**
     * 行程延迟队列（死信队列）
     */
    @Bean
    public Queue orderTtlQueue() {
        return QueueBuilder
                .durable(QueueEnum.QUEUE_TTL_ROUTE_CANCEL.getName())
                .withArgument("x-dead-letter-exchange", QueueEnum.QUEUE_ROUTE_CANCEL.getExchange())//到期后转发的交换机
                .withArgument("x-dead-letter-routing-key", QueueEnum.QUEUE_ROUTE_CANCEL.getRouteKey())//到期后转发的路由键
                .build();
    }

    /**
     * 将行程队列绑定到交换机
     */
    @Bean
    Binding orderBinding(DirectExchange orderDirect,Queue orderQueue){
        return BindingBuilder
                .bind(orderQueue)
                .to(orderDirect)
                .with(QueueEnum.QUEUE_ROUTE_CANCEL.getRouteKey());
    }

    /**
     * 将行程延迟取消队列绑定到交换机
     */
    @Bean
    Binding orderTtlBinding(DirectExchange orderTtlDirect,Queue orderTtlQueue){
        return BindingBuilder
                .bind(orderTtlQueue)
                .to(orderTtlDirect)
                .with(QueueEnum.QUEUE_TTL_ROUTE_CANCEL.getRouteKey());
    }

}

