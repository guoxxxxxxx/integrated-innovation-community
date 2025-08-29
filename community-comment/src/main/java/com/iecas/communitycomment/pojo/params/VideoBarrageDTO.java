package com.iecas.communitycomment.pojo.params;

import lombok.Data;

/**
 * @Time: 2025/8/28 16:13
 * @Author: guox
 * @File: BarrageDTO
 * @Description:
 */
@Data
public class VideoBarrageDTO {


    /**
     * 消息内容
     */
    private String data;

    /**
     * 视频id
     */
    private Long vid;

    /**
     * 视频播放进度
     */
    private Double videoPlayProgress;
}
