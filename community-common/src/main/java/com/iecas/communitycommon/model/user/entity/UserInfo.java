package com.iecas.communitycommon.model.user.entity;

import java.util.Date;
import java.io.Serializable;
import java.io.Serial;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (TbUserInfo)表实体类
 *
 * @author guox
 * @since 2025-02-05 20:16:12
 */
@Data
@Builder
public class UserInfo implements Serializable{
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户权限关联id
     */
    private Long authUserId;
    
    /**
     * 头像路径
     */
    private String avatar;
    
    /**
     * 生日
     */
    private Date birthday;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 性别
     */
    private String gender;
    
    /**
     * 用户昵称
     */
    private String nickname;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 注册时间
     */
    private Date registerTime;
}

