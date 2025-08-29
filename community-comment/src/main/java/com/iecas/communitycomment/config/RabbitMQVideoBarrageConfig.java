package com.iecas.communitycomment.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Time: 2025/8/29 21:30
 * @Author: guox
 * @File: RabbitMQConfig
 * @Description:
 */
@Configuration
public class RabbitMQVideoBarrageConfig {

    // 队列名
    public static final String BARRAGE_QUEUE = "videoBarrageQueue";
    // 交换机名
    public static final String BARRAGE_EXCHANGE = "videoBarrageExchange";
    // 路由键
    public static final String BARRAGE_ROUTING_KEY = "videoBarrageRoutingKey";


    /**
     * 声明弹幕队列，持久化
     */
    @Bean
    public Queue barrageQueue() {
        return new Queue(BARRAGE_QUEUE, true);
    }


    /**
     * 声明直连交换机
     */
    @Bean
    public DirectExchange barrageExchange() {
        return new DirectExchange(BARRAGE_EXCHANGE, true, false);
    }

    
    /**
     * 绑定队列和交换机
     */
    @Bean
    public Binding barrageBinding(Queue barrageQueue, DirectExchange barrageExchange) {
        return BindingBuilder.bind(barrageQueue)
                .to(barrageExchange)
                .with(BARRAGE_ROUTING_KEY);
    }
}
