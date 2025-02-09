/**
 * @Time: 2025/2/9 19:41
 * @Author: guoxun
 * @File: RabbitConfig
 * @Description:
 */

package com.iecas.communitycommon.config;


import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {


    /**
     * 创建一个配置为手动确认模式的 {@link SimpleRabbitListenerContainerFactory} 实例。
     * 该工厂用于创建一个监听容器，在手动确认模式下处理消息。在手动确认模式下，消费者
     * 需要在处理完消息后显式地发送确认（或负确认），这为消息消费和可靠性提供了更高的控制权。
     * @param connectionFactory 用于创建 RabbitMQ 连接的连接工厂
     * @return 配置为手动确认模式的 {@link SimpleRabbitListenerContainerFactory} 实例，能够用于监听消息。
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerManualAckContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }


    /**
     * 自定义消息转换器
     */
    @Bean
    public MessageConverter jsonToMapMessageConverter() {
        DefaultClassMapper defaultClassMapper = new DefaultClassMapper();
        defaultClassMapper.setTrustedPackages("com.iecas.communitycommon.event"); // trusted packages
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        jackson2JsonMessageConverter.setClassMapper(defaultClassMapper);
        return jackson2JsonMessageConverter;
    }


    /**
     * rabbitAdmin
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
