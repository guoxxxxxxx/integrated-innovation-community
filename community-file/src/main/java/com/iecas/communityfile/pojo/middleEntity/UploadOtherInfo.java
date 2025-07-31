/**
 * @Time: 2025/3/29 14:30
 * @Author: guoxun
 * @File: UploadOtherInfo
 * @Description:
 */

package com.iecas.communityfile.pojo.middleEntity;


import lombok.Data;

import java.util.ArrayList;


@Data
public class UploadOtherInfo {

    /**
     * 上传模式 NORMAL, VIDEO
     */
    private String mode;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 标签
     */
    private ArrayList<String> tags;
}
