package com.iecas.communityvideo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Time: 2025/8/10 15:48
 * @Author: guox
 * @File: StaticResourceConfig
 * @Description:
 */
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/videos/**")
                .addResourceLocations("file:/home/guox/file/output/");
    }
}
