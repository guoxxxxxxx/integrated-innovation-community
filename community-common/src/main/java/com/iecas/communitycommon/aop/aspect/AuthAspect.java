/**
 * @Time: 2025/2/11 19:57
 * @Author: guoxun
 * @File: AuthAspect
 * @Description: 鉴权切面
 */

package com.iecas.communitycommon.aop.aspect;


import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.constant.RedisPrefix;
import com.iecas.communitycommon.exception.AuthException;
import com.iecas.communitycommon.exception.CommonException;
import com.iecas.communitycommon.feign.AuthServiceFeign;
import com.iecas.communitycommon.model.auth.vo.TokenVO;
import com.iecas.communitycommon.utils.CommonResultUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;


@Aspect
@Slf4j
@Component
public class AuthAspect {

    @Autowired(required = false)
    AuthServiceFeign authServiceFeign;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Pointcut("@annotation(com.iecas.communitycommon.aop.annotation.Auth)")
    public void pointCut(){};


    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) throws Exception{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        // 从请求头部获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasLength(token)){
            String bearerToken = request.getHeader("Authorization");
            if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer ")){
                token = bearerToken.substring(7);
            }
        }
        if (!StringUtils.hasLength(token)){
            throw new AuthException("token不存在");
        }

        // 解析token
        CommonResult commonResult = authServiceFeign.parseToken(token);
        if (commonResult.getStatus() == 200){
            TokenVO parseToken = CommonResultUtils.parseCommonResult(commonResult, TokenVO.class);
            if (parseToken.getStatus()){
                // 从redis中获取对应的最新token信息
                String redisToken = stringRedisTemplate.opsForValue().get(RedisPrefix.AUTH_LOGIN_TOKEN
                        .getPath(parseToken.getParseClaims().getSub()));
                // 默认只鉴权用户是否登录
                if (StringUtils.hasLength(redisToken)){
                    log.info("用户: {}, 验证登录成功", parseToken.getParseClaims().getSub());
                } else {
                    throw new AuthException("登录过期, 请重新登录");
                }
                // 单点登录 TODO 需要添加条件判断条件
                if (!redisToken.equals(token)){
                    throw new AuthException("账号在其他设备登录, 若不是本人操作请修改密码");
                }
                // TODO 精细化鉴权待完成
            }
            else {
                log.warn(parseToken.getMessage());
                throw new AuthException("当前用户无权限");
            }
        }
        else {
            throw new RuntimeException("鉴权服务器异常");
        }
    }
}
