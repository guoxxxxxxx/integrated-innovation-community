package com.iecas.communitycommon.pojo.params;

import lombok.Data;

/**
 * @Time: 2025/8/16 16:44
 * @Author: guox
 * @File: CommonParams
 * @Description:
 */
@Data
public class PageParams {

    /**
     * 分页大小
     */
    Long pageSize = 10L;

    /**
     * 页码
     */
    Long pageNo = 0L;
}
