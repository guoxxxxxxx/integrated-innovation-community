package com.iecas.communitycommon.feign;


import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.config.feign.CommonFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "community-user-service", configuration = {CommonFeignConfig.class})
public interface UserServiceFeign {


    /**
     * 根据邮箱查询用户信息
     * @param email 用户邮箱
     * @return 用户信息
     */
    @GetMapping("/user/info/queryUserInfoByEmail")
    CommonResult queryUserInfoByEmail(@RequestParam String email);


    /**
     * 根据用户id查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("/user/info/{id}")
    CommonResult queryUserInfoById(@PathVariable(name = "id") Long id);


    /**
     * 根据多个用户id批量查询用户信息
     * @param ids 用户id列表
     * @return 用户列表信息
     */
    @GetMapping("/user/info/queryUserInfoByIds")
    CommonResult queryUserInfoByIds(@RequestParam List<Long> ids);


    /**
     * 根据用户id查询用户详细信息并映射为Map
     * @param ids 用户id列表
     * @return 用户详细信息Map
     */
    @GetMapping("/user/info/queryUserInfoByIds2Map")
    CommonResult queryUserInfoByIds2Map(@RequestParam List<Long> ids);
}
