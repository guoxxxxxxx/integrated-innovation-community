/**
 * @Time: 2025/2/19 22:15
 * @Author: guoxun
 * @File: CommonFeignConfig
 * @Description:
 */

package com.iecas.communitycommon.config.feign;


import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Configuration
public class CommonFeignConfig {


    /**
     * 请求拦截器，将token放入每一个feign的请求头中
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = null;
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes instanceof ServletRequestAttributes){
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                token = request.getHeader("token");
            }
            else if (requestAttributes instanceof CustomRequestAttributes){
                CustomRequestAttributes customAttributes = (CustomRequestAttributes) requestAttributes;
                token = customAttributes.getToken();
            }
            if (StringUtils.hasLength(token)){
                requestTemplate.header("token", token);
            }
        };
    }


    /**
     * 自定义错误解码器
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
}
