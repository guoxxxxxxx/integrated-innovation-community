package com.iecas.communitycomment.pojo.params;

import com.iecas.communitycommon.pojo.params.PageParams;
import lombok.Data;

/**
 * @Time: 2025/8/16 16:43
 * @Author: guox
 * @File: QueryCommentParams
 * @Description:
 */
@Data
public class QueryCommentParams extends PageParams {

    /**
     * 评论所属视频id
     */
    private Long vid;

    /**
     * 是否按照时间排序
     */
    private Boolean sortByDate = false;

    /**
     * 是否按照时间升序排序
     */
    private Boolean sortByDateAsc = true;

    /**
     * 是否按照热度排序
     */
    private Boolean sortByHot = false;

    /**
     * 是否按照热度升序排列
     */
    private Boolean sortByHotAsc = true;
}
