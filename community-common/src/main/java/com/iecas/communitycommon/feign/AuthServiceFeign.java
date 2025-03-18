/**
 * @Time: 2025/2/11 19:38
 * @Author: guoxun
 * @File: AuthFeign
 * @Description:
 */

package com.iecas.communitycommon.feign;


import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.config.feign.CommonFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "community-auth-service", configuration = {CommonFeignConfig.class})
public interface AuthServiceFeign {


    /**
     * 验证用户token是否正确
     * @param token 所要验证的token
     * @return 验证结果
     */
    @PostMapping("/auth/user/parseToken")
    CommonResult parseToken(@RequestParam String token);


    /**
     * 根据id获取角色名称
     * @param id 角色id
     * @return 角色名称
     */
    @GetMapping("/auth/role/queryRoleNameById")
    CommonResult queryRoleNameById(@RequestParam(value = "id") Long id);

}
