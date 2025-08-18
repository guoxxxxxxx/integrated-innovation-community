package com.iecas.communitycomment.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iecas.communitycomment.pojo.middle.CommentUserMiddleEntity;
import com.iecas.communitycomment.pojo.middle.ReplyMiddleEntity;
import com.iecas.communitycommon.model.comment.entity.VideoCommentInfo;
import com.iecas.communitycommon.model.comment.entity.VideoCommentReplyInfo;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @Time: 2025/8/17 16:52
 * @Author: guox
 * @File: VideoCommentVO
 * @Description:
 */
@Data
public class VideoCommentVO extends VideoCommentInfo {

    /**
     * 回复消息
     */
    private ReplyMiddleEntity reply;

    /**
     * 当前用户信息
     */
    private CommentUserMiddleEntity user;

    /**
     * 父id默认就是空
     */
    private Long parentId;

    /**
     * 所有用户的信息
     */
    @JsonIgnore
    private HashMap<Long, UserInfo> allUserInfo;


    /**
     * 获取用户地址
     * @return
     */
    public String getAddress(){
        return this.getIpCity();
    }

    /**
     * 获取当前用户的详细信息
     */
    public CommentUserMiddleEntity getUser(){
        UserInfo userInfo = allUserInfo.get(super.getUid());
        return new CommentUserMiddleEntity(userInfo.getId(), userInfo.getNickname(), userInfo.getAvatar());
    }
}
