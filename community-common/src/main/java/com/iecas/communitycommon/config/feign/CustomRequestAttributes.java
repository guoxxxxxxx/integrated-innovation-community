package com.iecas.communitycommon.config.feign;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Time: 2025/8/10 14:05
 * @Author: guox
 * @File: CustomRequestAttributes
 * @Description: 自定义请求属性
 */
@Data
public class CustomRequestAttributes implements RequestAttributes {

    private final String token;

    private final RequestAttributes delegate;

    public CustomRequestAttributes(RequestAttributes original) {
        this.delegate = original;
        String extractedToken = null;
        if (original instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) original).getRequest();
            extractedToken = request.getHeader("token");
            if (!StringUtils.hasLength(extractedToken)) {
                String bearer = request.getHeader("Authorization");
                if (StringUtils.hasLength(bearer) && bearer.startsWith("Bearer ")) {
                    extractedToken = bearer.substring(7);
                }
            }
        }
        this.token = extractedToken;
    }

    @Override
    public Object getAttribute(String name, int scope) {
        return delegate.getAttribute(name, scope);
    }

    @Override
    public void setAttribute(String name, Object value, int scope) {
        delegate.setAttribute(name, value, scope);
    }

    @Override
    public void removeAttribute(String name, int scope) {
        delegate.removeAttribute(name, scope);
    }

    @Override
    public String[] getAttributeNames(int scope) {
        return delegate.getAttributeNames(scope);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback, int scope) {
        delegate.registerDestructionCallback(name, callback, scope);
    }

    @Override
    public Object resolveReference(String key) {
        return delegate.resolveReference(key);
    }

    @Override
    public String getSessionId() {
        return delegate.getSessionId();
    }

    @Override
    public Object getSessionMutex() {
        return delegate.getSessionMutex();
    }
}
