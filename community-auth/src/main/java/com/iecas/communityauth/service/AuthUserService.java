package com.iecas.communityauth.service;

import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iecas.communityauth.dto.LoginDTO;
import com.iecas.communityauth.dto.RegisterDTO;
import com.iecas.communityauth.dto.ResetDTO;
import com.iecas.communityauth.entity.LoginUserInfo;
import com.iecas.communitycommon.model.auth.entity.AuthUser;
import com.iecas.communitycommon.model.auth.vo.TokenVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

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
    void register(RegisterDTO registerDTO, HttpServletRequest request);


    /**
     * 根据用户邮箱查询用户信息
     * @param email 用户邮箱
     * @return {@link LoginUserInfo}用户权限信息
     */
    @Cached(name = "auth:user:email2LoginUserInfo", expire = 120, key = "#email")
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


    /**
     * 重置密码
     * @param resetDTO 重置信息
     */
    void reset(ResetDTO resetDTO);


    /**
     * 用户登录  [密码 | 登录验证码] 二选一 密码优先
     * @param loginDTO 登录信息
     * @return token 用户信息token
     */
    String login(LoginDTO loginDTO);


    /**
     * 验证用户token是否合法
     * @param token 用户传递过来的token
     * @return {
     *     "message": "验证消息",
     *     "status": "验证结果",
     *     "claims": object
     * }
     */
    TokenVO parseToken(String token);
}

