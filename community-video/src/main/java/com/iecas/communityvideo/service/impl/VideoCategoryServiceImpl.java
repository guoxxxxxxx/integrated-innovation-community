package com.iecas.communityvideo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communitycommon.model.video.entity.VideoCategoryInfo;
import com.iecas.communityvideo.dao.VideoCategoryDao;
import com.iecas.communityvideo.service.VideoCategoryService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Time: 2025/8/25 20:41
 * @Author: guox
 * @File: VideoCategoryServiceImpl
 * @Description:
 */
@Service("video-category-service")
public class VideoCategoryServiceImpl extends ServiceImpl<VideoCategoryDao, VideoCategoryInfo> implements VideoCategoryService {


    @Override
    public HashMap<Long, VideoCategoryInfo> getVideoCategoryMapping() {
        return baseMapper.getVideoCategoryMapping();
    }


    @Override
    public List<VideoCategoryInfo> getAllVideoClass() {
        return baseMapper.selectList(null);
    }
}
