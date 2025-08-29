package com.iecas.communitycomment.pojo.params;

import lombok.Data;

/**
 * @Time: 2025/8/29 18:34
 * @Author: guox
 * @File: QueryVideoBarrageParams
 * @Description:
 */
@Data
public class QueryVideoBarrageParams {

    /**
     * 视频id
     */
    private Long vid;

    /**
     * 最大弹幕数量
     */
    private Integer maxCount;

    /**
     * 开始时间
     */
    private Double start;

    /**
     * 结束时间
     */
    private Double end;
}
