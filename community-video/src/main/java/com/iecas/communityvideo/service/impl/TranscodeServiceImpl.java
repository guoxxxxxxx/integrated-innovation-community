package com.iecas.communityvideo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communitycommon.model.video.entity.TranscodeInfo;
import com.iecas.communityvideo.dao.TranscodeDao;
import com.iecas.communityvideo.service.TranscodeService;
import org.springframework.stereotype.Service;

/**
 * @Time: 2025/8/8 21:17
 * @Author: guox
 * @File: TranscodeServiceImpl
 * @Description: 视频转码服务实现类
 */
@Service("transcode-service")
public class TranscodeServiceImpl extends ServiceImpl<TranscodeDao, TranscodeInfo> implements TranscodeService {


}
