package com.iecas.communityfile.pojo.middleEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Time: 2025/8/10 17:11
 * @Author: guox
 * @File: MultiResolutionPath
 * @Description: 视频多分辨率播放路径
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)     // 仅序列化非空字段
public class TranscodeResolutionDataAndStatus {

    /**
     * 是否转码成功
     */
    @JsonIgnore
    private boolean isOk = false;

    /**
     * master
     */
    private String master;

    /**
     * 360p分辨率
     */
    @JsonProperty("360p")
    private String _360p;

    /**
     * 480p
     */
    @JsonProperty("480p")
    private String _480p;

    /**
     * 720p
     */
    @JsonProperty("720p")
    private String _720p;

    /**
     * 1080p
     */
    @JsonProperty("1080p")
    private String _1080p;
}
