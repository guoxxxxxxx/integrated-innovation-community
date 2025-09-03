package com.iecas.communityfile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iecas.communitycommon.model.file.entity.UploadRecord;

import java.util.List;

/**
 * (UploadRecord)表服务接口
 *
 * @author guox
 * @since 2025-02-19 17:25:33
 */
public interface UploadRecordService extends IService<UploadRecord> {


    /**
     * 寻找到上传失败的任务以及临时文件超过一个月没有删除的任务
     * @return
     */
    List<UploadRecord> findExpiredTask();


    /**
     * 删除过期的临时文件
     * @return 删除的过期的临时文件数量
     */
    Integer deleteExpiredTmpFile();
}

