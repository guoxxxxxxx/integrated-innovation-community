package com.iecas.communityfile.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communitycommon.utils.FileUtils;
import com.iecas.communityfile.dao.UploadRecordDao;
import com.iecas.communitycommon.model.file.entity.UploadRecord;
import com.iecas.communityfile.service.UploadRecordService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * (UploadRecord)表服务实现类
 *
 * @author guox
 * @since 2025-02-19 17:25:33
 */
@Service("uploadRecordService")
public class UploadRecordServiceImpl extends ServiceImpl<UploadRecordDao, UploadRecord> implements UploadRecordService {


    @Override
    public List<UploadRecord> findExpiredTask() {
        List<UploadRecord> failList = baseMapper.selectList(new LambdaQueryWrapper<UploadRecord>()
                .eq(UploadRecord::getStatus, "FAIL")
                .or(
                        wrapper -> wrapper
                                .le(UploadRecord::getUploadStartTime, new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30)))
                                .eq(UploadRecord::getTmpFileStatus, true)
                ));
        return failList;
    }


    @Override
    public Integer deleteExpiredTmpFile() {
        // 寻找过期文件
        List<UploadRecord> expiredTaskList = findExpiredTask();
        // 删除过期临时文件
        int deleteCount = 0;
        for(UploadRecord e : expiredTaskList){
            Boolean deleted = FileUtils.delete(e.getTmpFileSavePath());
            if (deleted){
                deleteCount += 1;
            }
            e.setTmpFileStatus(false);
            baseMapper.updateById(e);
        }
        return deleteCount;
    }
}

