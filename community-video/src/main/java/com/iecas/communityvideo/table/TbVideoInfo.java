/**
 * @Time: 2025/3/20 16:53
 * @Author: guoxun
 * @File: TBVideoInfo
 * @Description: 视频投稿信息
 */

package com.iecas.communityvideo.table;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Entity
@Table(name = "tb_video_info")
public class TbVideoInfo {

    @Id
    @Comment("主键")
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "INT8 AUTO_INCREMENT")
    private Long id;

    @Comment("对应的文件的id")
    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @Comment("标题")
    @Column(name = "title")
    private String title;

    @Comment("视频标签")
    @Column(name = "tag")
    private String tag;

    @Comment("视频描述")
    @Column(name = "description")
    private String description;

    @Comment("上传用户id")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Comment("上传时间")
    @Column(name = "upload_time")
    private Date uploadTime;

    @Comment("修改时间")
    @Column(name = "modify_time")
    private Date modifyTime;

    @Comment("删除位")
    @Column(name = "deleted", columnDefinition = "BOOL DEFAULT false")
    private Boolean deleted;

    @Comment("封面图片")
    @Column(name = "cover_url")
    private String coverUrl;

    @Comment("视频时长/秒")
    @Column(name = "duration")
    private Long duration;

    @Comment("播放量")
    @Column(name = "view_count")
    private Long viewCount;

    @Comment("视频分辨率")
    @Column(name = "resolution")
    private String resolution;
}
