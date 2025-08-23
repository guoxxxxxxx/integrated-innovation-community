package com.iecas.communitycommon.model.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.Table;

/**
 * @Time: 2025/8/22 21:04
 * @Author: guox
 * @File: UserFollowInfo
 * @Description:
 */
@Table(name = "tb_user_follow")
public class UserFollowInfo {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关注者id
     */
    private Long source;

    /**
     * 被关注者id
     */
    private Long target;

    /**
     * 关注状态
     */
    private Boolean status;

    /**
     * 删除位
     */
    private Boolean deleted;
}
