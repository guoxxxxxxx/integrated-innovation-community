/**
 * @Time: 2025/2/11 19:38
 * @Author: guoxun
 * @File: AuthFeign
 * @Description:
 */

package com.iecas.communitycommon.feign;


import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "community-auth-service")
public interface AuthServiceFeign {


    /**
     * 验证用户token是否正确
     * @param token 所要验证的token
     * @return 验证结果：{
     *     "message": "提示消息",
     *     "status": true or false,
     *     "claims": Object, if status is true
     * }
     */
    @PostMapping("/auth/user/parseToken")
    CommonResult parseToken(@RequestParam String token);
}
