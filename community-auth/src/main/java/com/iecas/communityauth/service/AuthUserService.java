package com.iecas.communityauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iecas.communityauth.dto.RegisterDTO;
import com.iecas.communityauth.entity.LoginUserInfo;
import com.iecas.communitycommon.model.auth.entity.AuthUser;

import java.util.List;

/**
 * (AuthUser)表服务接口
 *
 * @author guox
 * @since 2025-02-05 20:32:13
 */
public interface AuthUserService extends IService<AuthUser> {

    /**
     * 用户注册
     * @param registerDTO 注册信息
     */
    void register(RegisterDTO registerDTO);


    /**
     * 根据用户邮箱查询用户信息
     * @param email 用户邮箱
     * @return {@link LoginUserInfo}用户权限信息
     */
    LoginUserInfo queryByUserEmail(String email);


    /**
     * 根据用户手机号查询用户详细信息
     * @param phone 用户手机号
     * @return {@link LoginUserInfo}用户权限信息
     */
    LoginUserInfo queryByUserPhone(String phone);


    /**
     * 根据用户id查询用户权限列表
     * @param id 用户id
     * @return {@link List<String>} 用户权限列表
     */
    List<String> queryPermissionById(Long id);

}

