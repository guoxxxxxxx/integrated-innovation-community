package com.iecas.communityvideo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iecas.communitycommon.common.PageResult;
import com.iecas.communitycommon.model.video.entity.VideoInfo;
import com.iecas.communityvideo.pojo.Params.QueryCondition;

/**
 * (VideoInfo)表服务接口
 *
 * @author guox
 * @since 2025-03-29 15:23:34
 */



public interface VideoInfoService extends IService<VideoInfo> {

    /**
     * 分页获取视频列表
     * @param condition 参数
     * @return 结果
     */
    PageResult<VideoInfo> getPage(QueryCondition condition);
}

