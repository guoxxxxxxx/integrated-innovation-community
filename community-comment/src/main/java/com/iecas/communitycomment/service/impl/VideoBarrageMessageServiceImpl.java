package com.iecas.communitycomment.service.impl;

import com.alibaba.fastjson2.JSON;
import com.iecas.communitycomment.config.RabbitMQVideoBarrageConfig;
import com.iecas.communitycomment.dao.VideoBarrageMessageRepository;
import com.iecas.communitycomment.pojo.params.QueryVideoBarrageParams;
import com.iecas.communitycomment.pojo.params.VideoBarrageDTO;
import com.iecas.communitycomment.pojo.vo.VideoBarrageVO;
import com.iecas.communitycomment.pojo.vo.VideoCommentVO;
import com.iecas.communitycomment.service.VideoBarrageMessageService;
import com.iecas.communitycomment.table.MGVideoBarrageMessage;
import com.iecas.communitycommon.common.UserThreadLocal;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Time: 2025/8/27 19:44
 * @Author: guox
 * @File: VideoBarrageMessageServiceImpl
 * @Description:
 */
@Service("video-barrage-message-service")
public class VideoBarrageMessageServiceImpl implements VideoBarrageMessageService {

    @Resource
    VideoBarrageMessageRepository videoBarrageMessageRepository;

    @Resource
    RabbitTemplate rabbitTemplate;


    @Override
    public List<MGVideoBarrageMessage> getBarrageMessageByVid(Long vid) {
        return videoBarrageMessageRepository.findMGVideoBarrageMessageByVid(vid);
    }


    @Override
    public List<MGVideoBarrageMessage> findMGVideoBarrageMessageByVideoPlayProgressBetween(Long vid, Double videoPlayProgressAfter, Double videoPlayProgressBefore, Pageable pageable) {
        return videoBarrageMessageRepository.findMGVideoBarrageMessageByVidAndVideoPlayProgressBetween(vid, videoPlayProgressAfter, videoPlayProgressBefore, pageable);
    }


    @Override
    public MGVideoBarrageMessage saveVideoBarrage(VideoBarrageDTO dto) {
        UserInfo currentUser = UserThreadLocal.getUserInfo();
        MGVideoBarrageMessage barrageMessage = MGVideoBarrageMessage.builder()
                .uid(currentUser.getId())
                .createTime(new Date())
                .deleted(false)
                .content(dto.getData())
                .vid(dto.getVid())
                .videoPlayProgress(dto.getVideoPlayProgress())
                .createTime(new Date())
                .build();
        rabbitTemplate.convertAndSend(RabbitMQVideoBarrageConfig.BARRAGE_EXCHANGE, RabbitMQVideoBarrageConfig.BARRAGE_ROUTING_KEY,
                JSON.toJSONString(barrageMessage));
        // videoBarrageMessageRepository.save(barrageMessage);
        return barrageMessage;
    }


    @Override
    public VideoBarrageVO getBarrageMessage(QueryVideoBarrageParams params) {
        PageRequest pageRequest = PageRequest.of(0, params.getMaxCount());
        List<MGVideoBarrageMessage> barrageList = videoBarrageMessageRepository.findMGVideoBarrageMessageByVidAndVideoPlayProgressBetween(params.getVid(),
                params.getStart(), params.getEnd(), pageRequest);
        return new VideoBarrageVO(barrageList.size(), barrageList);
    }
}
