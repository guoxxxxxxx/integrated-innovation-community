/**
 * @Time: 2025/2/9 19:15
 * @Author: guoxun
 * @File: RabbitMqConfig
 * @Description:
 */

package com.iecas.communityuserservice.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMqConfig {

    /**
     * 注册队列
     */
    @Bean
    public Queue registerQueue(){
        return new Queue("registerQueue", true);
    }


    /**
     * 注册交换机
     */
    @Bean
    public DirectExchange registerDirectExchange(){
        return new DirectExchange("registerDirectExchange");
    }


    /**
     * 绑定注册队列到注册交换机中
     * 路由: authService: 用户注册信息 -> registerDirectExchange -> registerQueue -> userService
     * @param registerDirectExchange 注册交换机
     * @param registerQueue 注册队列
     */
    @Bean
    public Binding bindingRegisterMq(DirectExchange registerDirectExchange, Queue registerQueue){
        return BindingBuilder.bind(registerQueue).to(registerDirectExchange).with("user.register");
    }
}
