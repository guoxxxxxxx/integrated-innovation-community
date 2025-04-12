package com.iecas.communityvideo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communityvideo.dao.VideoInfoDao;
import com.iecas.communitycommon.model.video.entity.VideoInfo;
import com.iecas.communityvideo.service.VideoInfoService;
import org.springframework.stereotype.Service;

/**
 * (VideoInfo)表服务实现类
 *
 * @author guox
 * @since 2025-03-29 15:23:35
 */



@Service("videoInfoService")
public class VideoInfoServiceImpl extends ServiceImpl<VideoInfoDao, VideoInfo> implements VideoInfoService {

}

