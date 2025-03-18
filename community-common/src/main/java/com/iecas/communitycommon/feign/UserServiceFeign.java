package com.iecas.communitycommon.feign;


import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.config.feign.CommonFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "community-user-service", configuration = {CommonFeignConfig.class})
public interface UserServiceFeign {


    /**
     * 根据邮箱查询用户信息
     * @param email 用户邮箱
     * @return 用户信息
     */
    @GetMapping("/user/info/queryUserInfoByEmail")
    CommonResult queryUserInfoByEmail(@RequestParam String email);
}
