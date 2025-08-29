package com.iecas.communitycomment.pojo.vo;

import com.iecas.communitycomment.table.MGVideoBarrageMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Time: 2025/8/29 15:56
 * @Author: guox
 * @File: VideoBarrageVO
 * @Description:
 */
@Data
@AllArgsConstructor
public class VideoBarrageVO {

    /**
     * 弹幕总条数
     */
    private Integer total;

    /**
     * 弹幕实体
     */
    private List<MGVideoBarrageMessage> data;
}
