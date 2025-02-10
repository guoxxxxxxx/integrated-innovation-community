# 融合创新社区

> 该项目目前正在开发中...

## 项目概述

“融合创新社区”是一个集内容分享、互动交流、文件存储和在线视频播放于一体的社区平台。用户可以发布帖子、评论交流、上传文件、在线观看视频，以促进知识共享和创新交流。



## 项目架构

### 微服务拆分

+ **API 网关（Gateway Service）**：提供统一的入口，进行路由、负载均衡、鉴权、限流等
+ **身份认证服务（Auth Service）**：负责用户注册、登录、权限管理
+ **用户管理服务（User Service）**：管理用户信息（个人资料、权限、账户安全）
+ **帖子管理服务（Post Service）**：处理帖子创建、编辑、删除、分类、标签
+ **评论服务（Comment Service）**：负责评论存储、回复、点赞、举报
+ **文件存储服务（File Service）**：管理用户上传的文件，提供访问控制
+ **视频流媒体服务（Video Service）**：处理视频上传、转码、播放、弹幕
+ **搜索 & 推荐服务（Search & Recommendation Service）**：提供全文搜索和智能推荐
+ **通知 & 消息服务（Notification Service）**：管理站内消息、邮件通知、WebSocket 实时推送
+ **后台管理服务（Admin Service）**：提供管理端 API，包括用户管理、内容审核、数据统计



## 数据库设计

### 身份认证微服务

#### 用户权限信息表

+ `tb_auth_permission`

| **字段名**    | **数据类型**   | **约束**                              | **默认值** | **注释** |
| ------------- | -------------- | ------------------------------------- | ---------- | -------- |
| `id`          | `bigint`       | `NOT NULL AUTO_INCREMENT PRIMARY KEY` | -          | 权限主键 |
| `deleted`     | `tinyint(1)`   | -                                     | `0`        | 删除位   |
| `description` | `varchar(255)` | -                                     | `NULL`     | 权限描述 |
| `name`        | `varchar(255)` | -                                     | `NULL`     | 权限名称 |


+ DDL

```sql
CREATE TABLE `tb_auth_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限主键',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除位',
  `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  `name` varchar(255) DEFAULT NULL COMMENT '权限名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```



#### 权限角色表

+ `tb_auth_role`

| **字段名**    | **数据类型**   | **约束**                              | **默认值** | **注释** |
| ------------- | -------------- | ------------------------------------- | ---------- | -------- |
| `id`          | `bigint`       | `NOT NULL AUTO_INCREMENT PRIMARY KEY` | -          | 角色主键 |
| `deleted`     | `tinyint(1)`   | -                                     | `0`        | 删除位   |
| `description` | `varchar(512)` | -                                     | `NULL`     | 角色描述 |
| `name`        | `varchar(32)`  | -                                     | `NULL`     | 角色名称 |


+ DDL

```sql
CREATE TABLE `tb_auth_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色主键',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除位',
  `description` varchar(512) DEFAULT NULL COMMENT '角色描述',
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```



#### 用户状态信息表

+ `tb_auth_user`

| **字段名**                | **数据类型**   | **约束**                              | **默认值** | **注释**         |
| ------------------------- | -------------- | ------------------------------------- | ---------- | ---------------- |
| `id`                      | `bigint`       | `NOT NULL AUTO_INCREMENT PRIMARY KEY` | -          | 主键             |
| `account_non_expired`     | `tinyint(1)`   | -                                     | `1`        | 账户是否没过期   |
| `account_non_locked`      | `tinyint(1)`   | -                                     | `1`        | 账户是否没被锁定 |
| `credentials_non_expired` | `tinyint(1)`   | -                                     | `1`        | 资格是否过期     |
| `deleted`                 | `tinyint(1)`   | -                                     | `0`        | 删除位           |
| `enabled`                 | `tinyint(1)`   | -                                     | `1`        | 是否可用         |
| `password`                | `varchar(255)` | -                                     | `NULL`     | 密码             |
| `role_id`                 | `bigint`       | -                                     | `NULL`     | 角色             |
| `last_login_time`         | `DATETIME`     | -                                     | -          | 上次登录时间     |
| `last_login_ip`           | `varchar(255)` | -                                     | -          | 上次登录ip       |
| `email`                   | `varchar(255)` | `唯一约束`                            | -          | 用户邮箱         |
| `phone`                   | `varchar(255)` | `唯一约束`                            | -          | 用户手机号       |


+ DDL

```sql
CREATE TABLE `tb_auth_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_non_expired` TINYINT ( 1 ) DEFAULT '1' COMMENT '账户是否没过期',
  `account_non_locked` TINYINT ( 1 ) DEFAULT '1' COMMENT '账户是否没被锁定',
  `credentials_non_expired` TINYINT ( 1 ) DEFAULT '1' COMMENT '资格是否过期',
  `deleted` TINYINT ( 1 ) DEFAULT '0' COMMENT '删除位',
  `enabled` TINYINT ( 1 ) DEFAULT '1' COMMENT '是否可用',
  `password` VARCHAR ( 255 ) DEFAULT NULL COMMENT '密码',
  `role_id` BIGINT DEFAULT NULL COMMENT '角色',
  `user_info_id` BIGINT DEFAULT NULL COMMENT '用户角色信息id',
  `username` VARCHAR ( 255 ) DEFAULT NULL COMMENT '用户名',
  `last_login_ip` VARCHAR ( 255 ) DEFAULT NULL COMMENT '上次登录ip',
  `last_login_time` DATETIME ( 6 ) DEFAULT NULL COMMENT '上次登录时间',
PRIMARY KEY ( `id` ) 
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
```



### 用户信息微服务

#### 用户信息信息表

+ `tb_user_info`

| **字段名**      | **数据类型**   | **约束**                              | **默认值** | **注释**        |
| --------------- | -------------- | ------------------------------------- | ---------- | --------------- |
| `id`            | `bigint`       | `NOT NULL AUTO_INCREMENT PRIMARY KEY` | -          | 主键            |
| `auth_user_id`  | `bigint`       | -                                     | `NULL`     | 用户权限关联 ID |
| `avatar`        | `varchar(255)` | -                                     | `NULL`     | 头像路径        |
| `birthday`      | `datetime(6)`  | -                                     | `NULL`     | 生日            |
| `email`         | `varchar(255)` | `UNIQUE`                              | `NULL`     | 邮箱            |
| `gender`        | `varchar(255)` | -                                     | `NULL`     | 性别            |
| `nickname`      | `varchar(64)`  | -                                     | `NULL`     | 用户昵称        |
| `phone`         | `varchar(255)` | `UNIQUE`                              | `NULL`     | 手机号          |
| `register_time` | `timestamp`    | `DEFAULT CURRENT_TIMESTAMP`           | `NULL`     | 注册时间        |


+ DDL

```sql
CREATE TABLE `tb_user_info` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `auth_user_id` BIGINT DEFAULT NULL COMMENT '用户权限关联 ID',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像路径',
  `birthday` DATETIME(6) DEFAULT NULL COMMENT '生日',
  `email` VARCHAR(255) DEFAULT NULL COMMENT '邮箱',
  `gender` VARCHAR(255) DEFAULT NULL COMMENT '性别',
  `nickname` VARCHAR(64) DEFAULT NULL COMMENT '用户昵称',
  `phone` VARCHAR(255) DEFAULT NULL COMMENT '手机号',
  `register_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6q12nthepxllo9l6cik15v153` (`email`),
  UNIQUE KEY `UK_8nt02q694b7nig5d0a3df26b` (`phone`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
```



## 微服务设计

### 公共模块

#### 日志输出

##### 功能

✅ **使用 AOP 统一管理日志**，在 **方法调用前后** 记录日志信息。  
✅ **使用 Spring Boot 自动装配**，当 `common` 作为依赖引入时，自动启用日志功能，而无需手动注册 Bean。  
✅ **支持 **`**@ConditionalOnMissingBean**`，确保如果用户提供了自定义日志配置，默认的日志 AOP 不会重复注入。

##### 实现细节

`Logger.java`

```java
/**
 * @Time: 2024/8/28 15:11
 * @Author: guoxun
 * @File: Logger
 * @Description:
 */

package com.iecas.communitycommon.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Logger {

    String value() default "";
}

```

`LogAspect.java`

```java
/**
 * @Time: 2024/8/28 15:12
 * @Author: guoxun
 * @File: LogAspect
 * @Description:
 */

package com.iecas.communitycommon.aop.aspect;

import com.alibaba.fastjson2.JSON;
import com.iecas.communitycommon.aop.annotation.Logger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Aspect
@Slf4j
@Component
public class LogAspect {

    @Pointcut("@annotation(com.iecas.communitycommon.aop.annotation.Logger)")
    public void pointCut(){}


    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("执行时间为: {} ms", endTime - startTime);
        return proceed;
    }


    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) throws Exception{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("请求 --> url: {}, method: {}, ip: {}, class_method: {}, args: {}",
                request.getRequestURI(), request.getMethod(), request.getRemoteAddr(), joinPoint.getSignature().getDeclaringType().getName()
                        + "." + joinPoint.getSignature().getName(), joinPoint.getArgs());
    }


    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void doAfter(JoinPoint joinPoint, Object result){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Logger logger = method.getAnnotation(Logger.class);
        String value = null;
        if (logger != null){
            value = logger.value();
        }
        log.info("{}方法返回值为: {}", value, JSON.toJSONString(result));
    }

}
```

`CommonAopAutoConfig.java`

```java
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

```

创建文件：

`src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`

文件中添加内容如下：

`com.iecas.communitycommon.config.autoConfiguration.CommonAopAutoConfig`

#### 全局异常处理

##### 功能

✅ **使用 **`**@RestControllerAdvice**`** 进行全局异常拦截**，确保 `controller` 层所有异常被统一处理。  
✅ **定义 **`**CommonException**`**，用于返回标准化的错误信息**。  
✅ **支持 **`**Spring Boot 自动装配**`，使 `common` 作为依赖时能 **自动注入全局异常处理**，无需手动配置。

##### 实现细节

`CommonGlobalExceptionHandler.java`

```java
/**
 * @Time: 2024/8/30 16:53
 * @Author: guoxun
 * @File: GlobalExceptionHandler
 * @Description:
 */

package com.iecas.communitycommon.config;


import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CommonGlobalExceptionHandler {


    /**
     * 全局异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e){
        log.error(e.toString());
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", new Date());
        response.put("status", 500);
        response.put("error", "Internal Server Error");
        response.put("message", e.toString());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * 公共异常, 向用户展示message中的信息
     * @param e CommonException
     * @return
     */
    @ExceptionHandler(CommonException.class)
    public CommonResult handleCommonException(CommonException e){
        return new CommonResult().status(5000).message(e.getMessage());
    }
}

```



`CommonException.java`

```java
/**
 * @Time: 2024/9/14 17:16
 * @Author: guoxun
 * @File: CommonException
 * @Description: 通用异常
 */

package com.iecas.communitycommon.exception;


public class CommonException extends RuntimeException{

    public CommonException(String message){
        super(message);
    }
}

```



### 鉴权微服务






## 接口设计

### 鉴权微服务

#### 公共模块

##### 向指定邮箱发送验证码接口

+ 方法类型：`GET`
+ 请求路径：`/auth/common/sendAutoCode`
+ 接口功能：向指定邮箱发送验证码
+ 参数

| 参数名   | 类型      | 必填 | 说明                   |
| -------- | --------- | ---- | ---------------------- |
| `email`  | `string`  | ✅    | 接收验证码的邮箱地址   |
| `length` | `integer` | ❌    | 验证码长度，默认值 `4` |


+ 响应示例

```json
{
    "timestamp": "2025-02-08T08:24:34.506+00:00",
    "message": "发送成功",
    "status": 200,
    "data": null
}
```



##### 用户注册逻辑

+ 方法类型：`POST`
+ 请求路径：`/auth/common/register`
+ 接口功能：用户注册
+ 参数
+

| 参数名     | 类型       | 必填 | 说明       |
| ---------- | ---------- | ---- | ---------- |
| `username` | `String`   | 是   | 用户名     |
| `password` | `String`   | 是   | 用户密码   |
| `authCode` | `authCode` | 是   | 注册验证码 |


+ 响应示例

```json
{
    "timestamp": "2025-02-10T05:58:03.632+00:00",
    "message": "注册成功",
    "status": 200,
    "data": null
}
```

+ 用户注册逻辑时序图

![](https://cdn.nlark.com/yuque/0/2025/png/29410528/1739173761744-aff8b7a9-261a-4828-8ddb-58595d9c5152.png)

