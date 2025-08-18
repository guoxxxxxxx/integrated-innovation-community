package com.iecas.communitycomment.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iecas.communitycomment.pojo.middle.CommentUserMiddleEntity;
import com.iecas.communitycomment.pojo.middle.ReplyMiddleEntity;
import com.iecas.communitycommon.model.comment.entity.VideoCommentReplyInfo;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import lombok.Data;

import java.util.HashMap;

/**
 * @Time: 2025/8/17 21:09
 * @Author: guox
 * @File: VideoReplyVO
 * @Description:
 */
@Data
public class VideoReplyVO extends VideoCommentReplyInfo {

    /**
     * 所有用户的信息
     */
    @JsonIgnore
    private HashMap<Long, UserInfo> allUserInfo;

    /**
     * 当前用户信息
     */
    private CommentUserMiddleEntity user;

    /**
     * 回复消息
     */
    private ReplyMiddleEntity reply;

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
