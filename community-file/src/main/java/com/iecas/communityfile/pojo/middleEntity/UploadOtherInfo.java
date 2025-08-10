/**
 * @Time: 2025/3/29 14:30
 * @Author: guoxun
 * @File: UploadOtherInfo
 * @Description:
 */

package com.iecas.communityfile.pojo.middleEntity;


import com.iecas.communityfile.pojo.middleEntity.metainfo.VideoMetadata;
import lombok.Data;
import java.util.List;


@Data
public class UploadOtherInfo {

    /**
     * 任务类型 NORMAL, VIDEO
     */
    private String taskType;

    /**
     * 视频文件对应的元数据信息
     */
    private VideoMetadata videoMetadata;
}
