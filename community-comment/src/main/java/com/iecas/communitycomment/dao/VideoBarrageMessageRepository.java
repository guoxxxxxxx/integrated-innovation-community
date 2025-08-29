package com.iecas.communitycomment.dao;

import com.iecas.communitycomment.table.MGVideoBarrageMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Time: 2025/8/27 19:37
 * @Author: guox
 * @File: VideoBarrageMessageRepository
 * @Description:
 */
public interface VideoBarrageMessageRepository extends MongoRepository<MGVideoBarrageMessage, String> {

    /**
     * 根据视频id获取弹幕列表
     * @param vid 视频id
     * @return 弹幕列表
     */
    List<MGVideoBarrageMessage> findMGVideoBarrageMessageByVid(Long vid);


    List<MGVideoBarrageMessage> findMGVideoBarrageMessageByVidAndVideoPlayProgressBetween(Long vid, Double videoPlayProgressAfter, Double videoPlayProgressBefore, Pageable pageable);

}
