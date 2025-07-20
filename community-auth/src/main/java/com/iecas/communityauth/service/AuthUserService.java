package com.iecas.communityauth.service;

import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iecas.communityauth.dto.LoginDTO;
import com.iecas.communityauth.dto.RegisterDTO;
import com.iecas.communityauth.dto.ResetDTO;
import com.iecas.communitycommon.model.auth.entity.AuthUser;
import com.iecas.communitycommon.model.auth.vo.TokenVO;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

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
    void register(RegisterDTO registerDTO, HttpServletRequest request);


    /**
     * 根据用户id查询用户权限列表
     * @param id 用户id
     * @return {@link List<String>} 用户权限列表
     */
    List<String> queryPermissionById(Long id);


    /**
     * 重置密码
     * @param resetDTO 重置信息
     * @return  true or false
     */
    boolean reset(ResetDTO resetDTO);


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


    /**
     * 根据用户token解析用户信息
     * @param token 用户token
     * @return 用户详细信息
     */
    UserInfo parseUserInfoByToken(String token);
}

