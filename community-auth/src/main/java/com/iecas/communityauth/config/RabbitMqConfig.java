/**
 * @Time: 2025/2/9 18:55
 * @Author: guoxun
 * @File: RabbitMQConfig
 * @Description:
 */

package com.iecas.communityauth.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {


    /**
     * 注册交换机: 用于在注册时给用户微服务传递用户注册消息，同步用户注册信息
     * 传递路由: registerDirectExchange -> user:registerReceiveQueue
     */
    @Bean
    public DirectExchange registerDirectExchange(){
        return new DirectExchange("registerDirectExchange");
    }
}
