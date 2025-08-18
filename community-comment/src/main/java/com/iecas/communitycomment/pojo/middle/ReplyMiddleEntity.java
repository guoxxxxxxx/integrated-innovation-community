package com.iecas.communitycomment.pojo.middle;

import com.iecas.communitycomment.pojo.vo.VideoReplyVO;
import lombok.Data;

import java.util.List;

/**
 * @Time: 2025/8/17 21:08
 * @Author: guox
 * @File: ReplyMiddleEntity
 * @Description:
 */
@Data
public class ReplyMiddleEntity {

    /**
     * 回复总数
     */
    private Long total;

    /**
     * 回复列表
     */
    List<VideoReplyVO> list;
}
