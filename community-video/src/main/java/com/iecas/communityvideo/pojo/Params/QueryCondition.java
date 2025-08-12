package com.iecas.communityvideo.pojo.Params;

import lombok.Data;

import java.util.Date;

/**
 * @Time: 2025/8/10 16:33
 * @Author: guox
 * @File: QueryCondition
 * @Description:
 */
@Data
public class QueryCondition {

    /**
     * 页码
     */
    private Integer pageNo;

    /**
     * 页面大小
     */
    private Integer pageSize = 16;

    /**
     * 类别
     */
    private String clazz;

    /**
     * 是否根据播放量排序
     */
    private boolean sortByViewsCount = false;

    /**
     * 根据播放量排序方式 如果为true则降序排序
     */
    private boolean sortByViewsCountDesc = false;

    /**
     * 上传者id
     */
    private Long uid;

    /**
     * 上传时间区间
     */
    private Date startUploadTime;
    private Date endUploadTime;

    /**
     * 是否根据上传时间排序
     */
    private boolean sortByUploadTime = false;

    /**
     * 是否根据上传时间降序排序
     */
    private boolean sortByUploadTimeDesc = false;
}
