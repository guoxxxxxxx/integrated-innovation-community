/**
 * @Time: 2025/2/6 19:22
 * @Author: guoxun
 * @File: CommonAutoConfiguration
 * @Description:
 */

package com.iecas.communitycommon.config.autoConfiguration;

import com.iecas.communitycommon.aop.aspect.LogAspect;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(AopAutoConfiguration.class)
@ConditionalOnMissingBean(LogAspect.class)
@ComponentScan("com.iecas.communitycommon.aop")
public class CommonAopAutoConfig {
}
