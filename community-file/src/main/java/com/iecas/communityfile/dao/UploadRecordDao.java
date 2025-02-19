package com.iecas.communityfile.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.iecas.communitycommon.model.file.entity.UploadRecord;

/**
 * (UploadRecord)表数据库访问层
 *
 * @author guox
 * @since 2025-02-19 17:25:27
 */
@Mapper
public interface UploadRecordDao extends BaseMapper<UploadRecord> {
}

