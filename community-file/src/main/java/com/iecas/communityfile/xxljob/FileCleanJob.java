package com.iecas.communityfile.xxljob;

import com.iecas.communitycommon.model.file.entity.UploadRecord;
import com.iecas.communityfile.service.UploadRecordService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Time: 2025/9/2 20:25
 * @Author: guox
 * @File: FileCleanJob
 * @Description:
 */
@Slf4j
@Component
public class FileCleanJob {

    @Resource
    UploadRecordService uploadRecordService;

    @XxlJob("failedFileCleanJob")
    public void cleanFailedUploadFiles() {
        log.info("==== 开始执行失败文件清理任务 ====");

        try {
            // 查询数据库，找出失败的任务以及临时文件超过一个月没有删除的任务
            Integer count = uploadRecordService.deleteExpiredTmpFile();
            log.info("==== 失败文件清理任务完成, 本次共删除: {} 个文件 ====", count);
        } catch (Exception e) {
            log.error("清理任务执行失败", e);
        }
    }
}
