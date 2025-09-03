package com.iecas.communitycommon.model.file.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * (UploadRecord)表实体类
 *
 * @author guox
 * @since 2025-02-19 17:25:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 删除标志位
     */
    private Integer deleted;
    
    /**
     * 对应上传文件的id
     */
    private Long fileId;
    
    /**
     * 文件名
     */
    private String filename;
    
    /**
     * 上传状态, FINISH: 完成, UPLOADING: 上传中, FAIL: 上传失败
     */
    private String status;
    
    /**
     * 上传完成时间
     */
    private Date uploadAchieveTime;
    
    /**
     * 上传开始时间
     */
    private Date uploadStartTime;
    
    /**
     * 上传用户id
     */
    private Long userId;

    /**
     * 临时文件的存储路径
     */
    private String tmpFileSavePath;

    /**
     * 临时文件的存在状态
     */
    private Boolean tmpFileStatus;

}

