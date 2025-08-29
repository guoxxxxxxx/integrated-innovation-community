package com.iecas.communitycomment.service;

import com.iecas.communitycomment.pojo.params.QueryVideoBarrageParams;
import com.iecas.communitycomment.pojo.params.VideoBarrageDTO;
import com.iecas.communitycomment.pojo.vo.VideoBarrageVO;
import com.iecas.communitycomment.table.MGVideoBarrageMessage;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Time: 2025/8/27 19:44
 * @Author: guox
 * @File: VideoBarrageMessageService
 * @Description:
 */
public interface VideoBarrageMessageService {

    /**
     * 根据视频id获取弹幕信息列表
     * @param vid 视频id
     * @return 弹幕信息列表
     */
    List<MGVideoBarrageMessage> getBarrageMessageByVid(Long vid);


    /**
     * 根据视频id查询指定时间戳之内的指定数量的数据
     * @param vid 视频id
     * @param timestampAfter 开始时间
     * @param timestampBefore 结束时间
     * @param pageable 分页信息
     * @return 指定数量的信息
     */
    List<MGVideoBarrageMessage> findMGVideoBarrageMessageByVideoPlayProgressBetween(Long vid, Double timestampAfter, Double timestampBefore, Pageable pageable);


    /**
     * 根据视频id保存弹幕信息
     * @param dto 弹幕信息
     * @return 保存状态
     */
    MGVideoBarrageMessage saveVideoBarrage(VideoBarrageDTO dto);


    /**
     * 根据查询条件查询弹幕信息
     * @param params 查询条件
     * @return 弹幕信息
     */
    VideoBarrageVO getBarrageMessage(QueryVideoBarrageParams params);
}
