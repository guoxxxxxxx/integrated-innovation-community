package com.iecas.communityfile.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communityfile.dao.UploadRecordDao;
import com.iecas.communitycommon.model.file.entity.UploadRecord;
import com.iecas.communityfile.service.UploadRecordService;
import org.springframework.stereotype.Service;

/**
 * (UploadRecord)表服务实现类
 *
 * @author guox
 * @since 2025-02-19 17:25:33
 */
@Service("uploadRecordService")
public class UploadRecordServiceImpl extends ServiceImpl<UploadRecordDao, UploadRecord> implements UploadRecordService {

}

