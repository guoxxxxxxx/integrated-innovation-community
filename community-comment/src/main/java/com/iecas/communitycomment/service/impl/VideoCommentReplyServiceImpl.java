package com.iecas.communitycomment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communitycomment.dao.VideoCommentReplyDao;
import com.iecas.communitycomment.pojo.params.VideoReplyDTO;
import com.iecas.communitycomment.service.VideoCommentReplyService;
import com.iecas.communitycommon.common.UserThreadLocal;
import com.iecas.communitycommon.model.comment.entity.VideoCommentReplyInfo;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import com.iecas.communitycommon.utils.IPUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Time: 2025/8/12 22:31
 * @Author: guox
 * @File: VideoCommentReplyServiceImpl
 * @Description:
 */
@Service("video-comment-reply-service")
public class VideoCommentReplyServiceImpl extends ServiceImpl<VideoCommentReplyDao, VideoCommentReplyInfo> implements VideoCommentReplyService {

    @Override
    public List<VideoCommentReplyInfo> getVideoCommentReplyByVCid(Long vcid) {
        List<VideoCommentReplyInfo> result = baseMapper.selectList(new LambdaQueryWrapper<VideoCommentReplyInfo>()
                .eq(VideoCommentReplyInfo::getVideoCommentId, vcid)
                .orderBy(true, false, VideoCommentReplyInfo::getCreateTime));
        return result;
    }


    @Override
    public Boolean saveVideoReply(VideoReplyDTO dto, HttpServletRequest request) {
        // 获取用户信息
        UserInfo userInfo = UserThreadLocal.getUserInfo();

        // 获取用户ip地址
        String ip = IPUtils.getIp(request);

        // 构建回复记录
        VideoCommentReplyInfo commentReplyInfo = VideoCommentReplyInfo.builder()
                .content(dto.getContent())
                .createTime(new Date().getTime())
                .ipAddress(ip)
                .parentId(dto.getParentId())
                .uid(userInfo.getId())
                .videoCommentId(dto.getVideoCommentId())
                .build();

        int insert = baseMapper.insert(commentReplyInfo);

        return insert == 1;
    }
}
