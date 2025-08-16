package com.iecas.communitycomment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communitycomment.dao.VideoCommentDao;
import com.iecas.communitycomment.pojo.params.QueryCommentParams;
import com.iecas.communitycomment.pojo.params.VideoCommentDTO;
import com.iecas.communitycomment.service.VideoCommentReplyService;
import com.iecas.communitycomment.service.VideoCommentService;
import com.iecas.communitycommon.common.PageResult;
import com.iecas.communitycommon.common.UserThreadLocal;
import com.iecas.communitycommon.model.comment.entity.VideoCommentInfo;
import com.iecas.communitycommon.model.comment.entity.VideoCommentReplyInfo;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import com.iecas.communitycommon.utils.IPUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Time: 2025/8/12 22:29
 * @Author: guox
 * @File: VideoCommentServiceImpl
 * @Description:
 */
@Service("video-comment-service")
public class VideoCommentServiceImpl extends ServiceImpl<VideoCommentDao, VideoCommentInfo> implements VideoCommentService {

    @Resource
    private VideoCommentReplyService videoCommentReplyService;

    @Override
    public PageResult<VideoCommentInfo> getVideoCommentById(QueryCommentParams params) {
        Page<VideoCommentInfo> videoCommentInfoPage = new Page<>(params.getPageNo(), params.getPageSize());
        Page<VideoCommentInfo> pageResult = baseMapper.selectPage(videoCommentInfoPage, new LambdaQueryWrapper<VideoCommentInfo>()
                .eq(VideoCommentInfo::getVid, params.getVid())
                .orderBy(params.getSortByDate(), params.getSortByDateAsc(), VideoCommentInfo::getCreateTime)
                .orderBy(params.getSortByHot(), params.getSortByHotAsc(), VideoCommentInfo::getLikes));

        // 查询每条评论所包含的子评论信息
        List<VideoCommentInfo> records = pageResult.getRecords();
        for (VideoCommentInfo e : records){
            List<VideoCommentReplyInfo> subReply = videoCommentReplyService.getVideoCommentReplyByVCid(e.getId());
            e.setReply(subReply);
        }

        return new PageResult<>(pageResult);
    }


    @Override
    public boolean saveOneVideComment(VideoCommentDTO dto, HttpServletRequest request) {
        // 获取用户信息
        UserInfo userInfo = UserThreadLocal.getUserInfo();

        // 获取用户的ip地址
        String ip = IPUtils.getIp(request);

        // 构建评论体
        VideoCommentInfo videoCommentInfo = VideoCommentInfo.builder()
                .uid(userInfo.getId())
                .content(dto.getContent())
                .vid(dto.getVid())
                .createTime(new Date().getTime())
                .ipAddress(ip)
                .build();

        int insert = baseMapper.insert(videoCommentInfo);

        return insert == 1;
    }
}
