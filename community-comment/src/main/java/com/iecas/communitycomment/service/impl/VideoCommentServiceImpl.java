package com.iecas.communitycomment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.iecas.communitycomment.dao.VideoCommentDao;
import com.iecas.communitycomment.pojo.middle.ReplyMiddleEntity;
import com.iecas.communitycomment.pojo.params.QueryCommentParams;
import com.iecas.communitycomment.pojo.params.VideoCommentDTO;
import com.iecas.communitycomment.pojo.vo.VideoCommentVO;
import com.iecas.communitycomment.pojo.vo.VideoReplyVO;
import com.iecas.communitycomment.service.VideoCommentReplyService;
import com.iecas.communitycomment.service.VideoCommentService;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.common.PageResult;
import com.iecas.communitycommon.common.UserThreadLocal;
import com.iecas.communitycommon.feign.UserServiceFeign;
import com.iecas.communitycommon.model.comment.entity.VideoCommentInfo;
import com.iecas.communitycommon.model.comment.entity.VideoCommentReplyInfo;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import com.iecas.communitycommon.utils.CommonResultUtils;
import com.iecas.communitycommon.utils.IPUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    @Resource
    private UserServiceFeign userServiceFeign;

    @Override
    public PageResult<VideoCommentVO> getVideoCommentById(QueryCommentParams params) {
        Page<VideoCommentInfo> videoCommentInfoPage = new Page<>(params.getPageNo(), params.getPageSize());
        Page<VideoCommentInfo> pageResult = baseMapper.selectPage(videoCommentInfoPage, new LambdaQueryWrapper<VideoCommentInfo>()
                .eq(VideoCommentInfo::getVid, params.getVid())
                .orderBy(params.getSortByDate(), params.getSortByDateAsc(), VideoCommentInfo::getCreateTime)
                .orderBy(params.getSortByHot(), params.getSortByHotAsc(), VideoCommentInfo::getLikes));

        // 查询每条评论所包含的子评论信息
        List<VideoCommentInfo> records = pageResult.getRecords();
        List<VideoCommentVO> result = new ArrayList<>();
        // 查询所要查询的用户信息id列表
        List<Long> userIds = new ArrayList<>();
        HashMap<Long, UserInfo> userInfoMap = new HashMap<>();
        for (VideoCommentInfo e : records){
            if (!userIds.contains(e.getUid())){
                userIds.add(e.getUid());
            }
            List<VideoCommentReplyInfo> subReply = videoCommentReplyService.getVideoCommentReplyByVCid(e.getId());
            List<VideoReplyVO> replyVOList = new ArrayList<>();
            for(VideoCommentReplyInfo o : subReply){
                if (!userIds.contains(o.getUid())){
                    userIds.add(o.getUid());
                }
                VideoReplyVO replyVO = new VideoReplyVO();
                BeanUtils.copyProperties(o, replyVO);
                replyVO.setAllUserInfo(userInfoMap);
                replyVOList.add(replyVO);
            }
            VideoCommentVO videoCommentVO = new VideoCommentVO();
            ReplyMiddleEntity replyMiddleEntity = new ReplyMiddleEntity();
            replyMiddleEntity.setTotal((long)subReply.size());
            replyMiddleEntity.setList(replyVOList);
            videoCommentVO.setReply(replyMiddleEntity);
            videoCommentVO.setAllUserInfo(userInfoMap);
            videoCommentVO.setIpCity(e.getIpCity());
            BeanUtils.copyProperties(e, videoCommentVO);
            result.add(videoCommentVO);
        }

        // 根据用户id查询用户的详细信息
        if (!userIds.isEmpty()) {
            CommonResult commonResult = userServiceFeign.queryUserInfoByIds2Map(userIds);
            userInfoMap.putAll(CommonResultUtils.parseCommonResult(commonResult, new TypeReference<>() {
            }));
        }
        return new PageResult<>(result, pageResult.getTotal(), pageResult.getCurrent(), pageResult.getSize());
    }


    @Override
    public VideoCommentInfo saveOneVideComment(VideoCommentDTO dto, HttpServletRequest request) {
        // 获取用户信息
        UserInfo userInfo = UserThreadLocal.getUserInfo();

        // 获取用户的ip地址
        String ip = IPUtils.getIp(request);

        // 构建评论体
        VideoCommentInfo videoCommentInfo = VideoCommentInfo.builder()
                .uid(userInfo.getId())
                .content(dto.getContent())
                .vid(dto.getVid())
                .createTime(new Date())
                .ipAddress(ip)
                .build();

        baseMapper.insert(videoCommentInfo);

        return videoCommentInfo;
    }
}
