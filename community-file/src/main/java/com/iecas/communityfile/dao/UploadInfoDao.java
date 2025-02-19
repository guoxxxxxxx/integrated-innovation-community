package com.iecas.communityfile.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iecas.communitycommon.model.file.entity.FileInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * (UploadInfo)表数据库访问层
 *
 * @author guox
 * @since 2025-02-17 21:29:36
 */
@Mapper
public interface UploadInfoDao extends BaseMapper<FileInfo> {

}

