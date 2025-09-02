package com.iecas.communityfile.xxljob;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Time: 2025/9/2 20:25
 * @Author: guox
 * @File: FileCleanJob
 * @Description:
 */
@Slf4j
@Component
public class FileCleanJob {

    @XxlJob("failedFileCleanJob")
    public void cleanFailedUploadFiles() {
        log.info("==== 开始执行失败文件清理任务 ====");

        try {
            // TODO 清理任务待完成
            // 1. 查询数据库或 Redis，找出失败任务
            // List<FileTask> failedTasks = fileTaskService.findFailedTasks();

            // 2. 遍历删除文件（缓存目录、Redis记录等）
            // for (FileTask task : failedTasks) {
            //     fileUtils.delete(task.getFilePath());
            //     fileTaskService.remove(task.getId());
            // }

            log.info("==== 失败文件清理任务完成 ====");
        } catch (Exception e) {
            log.error("清理任务执行失败", e);
        }
    }
}
