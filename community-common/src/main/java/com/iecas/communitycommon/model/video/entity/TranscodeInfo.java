package com.iecas.communitycommon.model.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (TranscodeInfo)实体类
 * @author guox
 * @since 2025-08-08 21:06:22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_transcode_info")
public class TranscodeInfo implements Serializable {

    private static final long serialVersionUID = -94874400043555678L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 完成时间
     */
    private Date achievedTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 转码状态: PENDING 等待中, PROCESSIONG 进行中, COMPLETED 完成, FAILED 失败
     */
    private String status;

    /**
     * 转码后的多分辨率路径 JSON格式
     */
    private String transcodePath;

    /**
     * 对应的视频id
     */
    private Long vid;
}

