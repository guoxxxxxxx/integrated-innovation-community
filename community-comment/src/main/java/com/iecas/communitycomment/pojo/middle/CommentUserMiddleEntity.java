package com.iecas.communitycomment.pojo.middle;

import lombok.Data;

/**
 * @Time: 2025/8/17 21:24
 * @Author: guox
 * @File: CommentUserMiddleEntity
 * @Description:
 */
@Data
public class CommentUserMiddleEntity {

    /**
     *  当前用户id
     */
    private Long id;

    /**
     * 当前用户名称
     */
    private String username;

    /**
     * 当前用户头像信息
     */
    private String avatar;


    public CommentUserMiddleEntity(Long id, String username, String avatar){
        this.id = id;
        this.username = username;
        this.avatar = avatar;
    }
}
