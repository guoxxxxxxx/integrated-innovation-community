package com.iecas.communitycommon.config.autoConfiguration;

import com.iecas.communitycommon.config.MyBatisPlusConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Time: 2025/8/10 17:05
 * @Author: guox
 * @File: CommonMyBatisPlusAutoConfig
 * @Description: MyBatis-Plus配置类自动装配
 */
@Configuration
@AutoConfigureAfter(Configuration.class)
@ConditionalOnMissingBean(MyBatisPlusConfig.class)
@Import(MyBatisPlusConfig.class)
public class CommonMyBatisPlusAutoConfig {
}
