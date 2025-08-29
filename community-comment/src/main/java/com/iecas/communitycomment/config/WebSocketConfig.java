package com.iecas.communitycomment.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Time: 2025/8/29 14:34
 * @Author: guox
 * @File: WebSocketConfig
 * @Description:
 */
@Slf4j
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        log.info("socket配置已注入!");
        return new ServerEndpointExporter();
    }
}
