package com.iecas.communityvideo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iecas.communitycommon.model.video.entity.VideoCategoryInfo;

import java.util.HashMap;
import java.util.List;

/**
 * @Time: 2025/8/25 20:40
 * @Author: guox
 * @File: VideoCategoryService
 * @Description:
 */
public interface VideoCategoryService extends IService<VideoCategoryInfo> {


    /**
     * 获取类别id和类别名称的映射
     * @return mapping
     */
    HashMap<Long, VideoCategoryInfo> getVideoCategoryMapping();


    /**
     * 获取视频的全部类别
     * @return 视频全部类别
     */
    List<VideoCategoryInfo> getAllVideoClass();
}
