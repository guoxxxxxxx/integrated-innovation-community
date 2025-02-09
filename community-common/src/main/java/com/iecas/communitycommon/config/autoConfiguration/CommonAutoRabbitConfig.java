/**
 * @Time: 2025/2/9 20:21
 * @Author: guoxun
 * @File: CommonAutoRabbitConfig
 * @Description:
 */

package com.iecas.communitycommon.config.autoConfiguration;


import com.iecas.communitycommon.aop.aspect.LogAspect;
import com.iecas.communitycommon.config.RabbitConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@AutoConfigureAfter(Configuration.class)
@ConditionalOnMissingBean(RabbitConfig.class)
@Import(RabbitConfig.class)
public class CommonAutoRabbitConfig {
}
