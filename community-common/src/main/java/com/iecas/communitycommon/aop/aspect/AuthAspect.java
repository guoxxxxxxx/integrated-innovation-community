/**
 * @Time: 2025/2/11 19:57
 * @Author: guoxun
 * @File: AuthAspect
 * @Description: 鉴权切面
 */

package com.iecas.communitycommon.aop.aspect;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.iecas.communitycommon.aop.annotation.Auth;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.common.UserThreadLocal;
import com.iecas.communitycommon.config.feign.CustomRequestAttributes;
import com.iecas.communitycommon.constant.HttpStatusEnum;
import com.iecas.communitycommon.constant.RedisPrefix;
import com.iecas.communitycommon.exception.AuthException;
import com.iecas.communitycommon.feign.AuthServiceFeign;
import com.iecas.communitycommon.feign.UserServiceFeign;
import com.iecas.communitycommon.model.auth.vo.TokenVO;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import com.iecas.communitycommon.utils.CommonResultUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Aspect
@Slf4j
@Component
public class AuthAspect {

    @Autowired(required = false)
    AuthServiceFeign authServiceFeign;

    @Autowired
    UserServiceFeign userServiceFeign;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Pointcut("@annotation(com.iecas.communitycommon.aop.annotation.Auth)")
    public void pointCut(){}


    /**
     * 用户鉴权
     */
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint){

        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        String token = null;
        if (attributes instanceof ServletRequestAttributes) {
            ServletRequestAttributes servletAttributes = (ServletRequestAttributes) attributes;
            HttpServletRequest request = servletAttributes.getRequest();
            // 从请求头部获取token
            token = request.getHeader("token");
            if (!StringUtils.hasLength(token)){
                String bearerToken = request.getHeader("Authorization");
                if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer ")){
                    token = bearerToken.substring(7);
                }
            }
        } else if (attributes instanceof CustomRequestAttributes) {
            CustomRequestAttributes customAttributes = (CustomRequestAttributes) attributes;
            token = customAttributes.getToken();
        }

        if (!StringUtils.hasLength(token)){
            // 不存在token时告知前端跳转到登陆界面
            throw new AuthException("Header中不存在token", HttpStatusEnum.AUTH_JUMP_LOGIN.getStatusCode());
        }

        // 解析token
        CommonResult commonResult = authServiceFeign.parseToken(token);
        if (commonResult.getStatus() == 200){
            TokenVO parseResult = CommonResultUtils.parseCommonResult(commonResult, TokenVO.class);
            if (parseResult.getStatus()){
                // 提取解析后的信息
                JSONObject currentUserInfo = JSON.parseObject(JSON.toJSONString(parseResult.getParsedData()));
                // 从redis中获取对应的最新token信息
                String redisToken = stringRedisTemplate.opsForValue().get(RedisPrefix.AUTH_LOGIN_TOKEN
                        .getPath(currentUserInfo.getString("sub")));

                // 解析redis中token的信息
                CommonResult redisTokenInfo = authServiceFeign.parseToken(redisToken);
                TokenVO tokenVO = CommonResultUtils.parseCommonResult(redisTokenInfo, TokenVO.class);
                Map<String, Object> parsedData = (Map<String, Object>) tokenVO.getParsedData();
                Map<String, Object> tokenEntity = (Map<String, Object>) parsedData.get("data");
                // 根据用户邮箱查询用户信息
                CommonResult userInfoResult = userServiceFeign.queryUserInfoByEmail((String) tokenEntity.get("email"));
                UserInfo contextUserInfo = CommonResultUtils.parseCommonResult(userInfoResult, UserInfo.class);

                BeanUtils.copyProperties(parsedData.get("data"), contextUserInfo);
                UserThreadLocal.setUserInfo(contextUserInfo);

                // 默认只鉴权用户是否登录
                if (StringUtils.hasLength(redisToken)){
                    log.info("用户: {}, 验证登录成功", currentUserInfo.getString("sub"));
                } else {
                    throw new AuthException("登录过期, 请重新登录", HttpStatusEnum.AUTH_JUMP_LOGIN.getStatusCode());
                }
                // 获取注解
                MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
                Method method = methodSignature.getMethod();
                Auth authAnnotation = method.getAnnotation(Auth.class);
                String[] needPermissions = {};
                String[] permitRole = {};
                if (authAnnotation != null){
                    needPermissions = authAnnotation.needPermissions();
                    permitRole = authAnnotation.permitRole();
                }

                if (needPermissions.length == 0 && permitRole.length == 0){
                    log.info("用户: {}, 验证权限成功", currentUserInfo.getString("sub"));
                }

                if(permitRole.length > 0){
                    List<String> roleList = Arrays.asList(permitRole);
                    // 根据roleId查询用户角色名称
                    JSONObject UserInfoDetails = (JSONObject) currentUserInfo.get("data");
                    CommonResult roleNameResult = authServiceFeign.queryRoleNameById(UserInfoDetails.getLong("roleId"));
                    String currentUserRoleName = (String) roleNameResult.getData();
                    if (currentUserRoleName.equals("null") || !StringUtils.hasLength(currentUserRoleName)
                            || !roleList.contains(currentUserRoleName)){
                        throw new AuthException("当前用户无权限");
                    }
                }
            }
            else if(parseResult.getHttpCode() == HttpStatusEnum.AUTH_JUMP_LOGIN.getStatusCode()){
                log.info("当前登录用户token已经过期，需要重新登录");
                throw new AuthException("用户token已过期", HttpStatusEnum.AUTH_JUMP_LOGIN.getStatusCode());
            }
            else {
                log.warn(parseResult.getMessage());
                throw new AuthException("当前用户无权限");
            }
        }
        else {
            throw new RuntimeException("鉴权服务器异常");
        }
    }


    @After("pointCut()")
    public void doAfter(JoinPoint joinPoint){
        // 清除用户线程变量
        UserThreadLocal.removeUser();
    }
}
