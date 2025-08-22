package com.iecas.communitycomment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.iecas.communitycomment.dao.VideoCommentReplyDao;
import com.iecas.communitycomment.pojo.middle.ReplyMiddleEntity;
import com.iecas.communitycomment.pojo.params.VideoReplyDTO;
import com.iecas.communitycomment.pojo.vo.VideoReplyVO;
import com.iecas.communitycomment.service.VideoCommentReplyService;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.common.PageResult;
import com.iecas.communitycommon.common.UserThreadLocal;
import com.iecas.communitycommon.feign.UserServiceFeign;
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
 * @Time: 2025/8/12 22:31
 * @Author: guox
 * @File: VideoCommentReplyServiceImpl
 * @Description:
 */
@Service("video-comment-reply-service")
public class VideoCommentReplyServiceImpl extends ServiceImpl<VideoCommentReplyDao, VideoCommentReplyInfo> implements VideoCommentReplyService {


    @Resource
    UserServiceFeign userServiceFeign;


    @Override
    public List<VideoCommentReplyInfo> getVideoCommentReplyByVCid(Long parentId) {
        List<VideoCommentReplyInfo> result = baseMapper.selectList(new LambdaQueryWrapper<VideoCommentReplyInfo>()
                .eq(VideoCommentReplyInfo::getParentId, parentId)
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
                .createTime(new Date())
                .ipAddress(ip)
                .parentId(dto.getParentId())
                .uid(userInfo.getId())
                .build();

        int insert = baseMapper.insert(commentReplyInfo);

        return insert == 1;
    }


    @Override
    public PageResult<VideoCommentReplyInfo> getVideoCommentReplyPageByVCid(Long parentId, Long pageNo, Long pageSize) {
        if(pageNo == null){
            pageNo = 1L;
        }
        if (pageSize == null){
            pageSize = 5L;
        }

        Page<VideoCommentReplyInfo> page = new Page<>(pageNo, pageSize);
        Page<VideoCommentReplyInfo> replyInfoPage = baseMapper.selectPage(page, new LambdaQueryWrapper<VideoCommentReplyInfo>()
                .eq(VideoCommentReplyInfo::getParentId, parentId));
        return new PageResult<>(replyInfoPage);
    }


    @Override
    public ReplyMiddleEntity getVideoCommentReplyPageFormatByVCid(Long parentId, Long pageNo, Long pageSize) {
        PageResult<VideoCommentReplyInfo> videoCommentReplyPageByVCid = getVideoCommentReplyPageByVCid(parentId, pageNo, pageSize);
        ReplyMiddleEntity result = new ReplyMiddleEntity();
        result.setTotal(videoCommentReplyPageByVCid.getTotal());
        List<VideoCommentReplyInfo> data = videoCommentReplyPageByVCid.getData();
        result.setList(new ArrayList<>());
        HashMap<Long, UserInfo> allUserInfo = new HashMap<>();
        List<Long> allUserId = new ArrayList<>();
        // 补充data中的数据
        for(VideoCommentReplyInfo e : data){
            VideoReplyVO replyVO = new VideoReplyVO();
            BeanUtils.copyProperties(e, replyVO);
            allUserId.add(e.getUid());
            replyVO.setAllUserInfo(allUserInfo);
            result.getList().add(replyVO);
        }
        // 查询用户信息
        CommonResult commonResult = userServiceFeign.queryUserInfoByIds2Map(allUserId);
        allUserInfo.putAll(CommonResultUtils.parseCommonResult(commonResult, new TypeReference<>() {}));

        return result;
    }
}
