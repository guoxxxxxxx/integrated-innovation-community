/**
 * @Time: 2024/8/30 16:53
 * @Author: guoxun
 * @File: GlobalExceptionHandler
 * @Description:
 */

package com.iecas.communitycommon.config;


import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.exception.AuthException;
import com.iecas.communitycommon.exception.CommonException;
import com.iecas.communitycommon.exception.IgnoreException;
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
        return new CommonResult().status(5500).message(e.getMessage());
    }


    /**
     * 权限异常
     */
    @ExceptionHandler(AuthException.class)
    public CommonResult handleAuthException(AuthException e){
        return new CommonResult().status(e.getStatusCode()).message(e.getMessage());
    }


    /**
     * 可以忽略的异常
     */
    @ExceptionHandler(IgnoreException.class)
    public CommonResult handleIgnoreException(IgnoreException e){
        return new CommonResult().status(5200).message(e.getMessage());
    }
}
