package com.iecas.communitycomment.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @Time: 2025/8/27 18:42
 * @Author: guox
 * @File: MGBarrageMessage
 * @Description: 弹幕信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "mg_video_barrage_message")
public class MGVideoBarrageMessage {

    @Id
    private String id;

    /**
     * 视频id
     */
    private Long vid;

    /**
     * 发送弹幕的用户的id
     */
    private Long uid;

    /**
     * 弹幕内容
     */
    private String content;

    /**
     * 对应于视频的播放时间节点
     */
    private Double videoPlayProgress;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除位
     */
    private Boolean deleted;
}
