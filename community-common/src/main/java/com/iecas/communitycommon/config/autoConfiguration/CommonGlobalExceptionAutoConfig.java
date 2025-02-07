/**
 * @Time: 2025/2/6 20:34
 * @Author: guoxun
 * @File: CommonGlobalExceptionAutoConfig
 * @Description:
 */

package com.iecas.communitycommon.config.autoConfiguration;


import com.iecas.communitycommon.config.CommonGlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureAfter(Exception.class)
@ConditionalOnMissingBean(CommonGlobalExceptionHandler.class)
@Import(CommonGlobalExceptionHandler.class)
public class CommonGlobalExceptionAutoConfig {
}
