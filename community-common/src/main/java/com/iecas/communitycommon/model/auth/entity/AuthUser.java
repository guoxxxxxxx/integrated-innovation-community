package com.iecas.communitycommon.model.auth.entity;

import java.io.Serializable;
import java.io.Serial;

import lombok.Builder;
import lombok.Data;

/**
 * (AuthUser)表实体类
 *
 * @author guox
 * @since 2025-02-05 20:07:57
 */
@Data
public class AuthUser implements Serializable{
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    
    /**
     * 主键
     */
    private Long id;
    
    /**
     * 账户是否没过期
     */
    private Integer accountNonExpired;
    
    /**
     * 账户是否没被锁定
     */
    private Integer accountNonLocked;
    
    /**
     * 资格是否过期
     */
    private Integer credentialsNonExpired;
    
    /**
     * 删除位
     */
    private Integer deleted;
    
    /**
     * 是否可用
     */
    private Integer enabled;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 角色
     */
    private Long roleId;
    
    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 上次登录ip
     */
    private String lastLoginIp;
    
    /**
     * 上次登录时间
     */
    private Object lastLoginTime;
}

