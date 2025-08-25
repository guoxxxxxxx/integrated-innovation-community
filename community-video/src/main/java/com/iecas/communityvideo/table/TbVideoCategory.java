package com.iecas.communityvideo.table;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @Time: 2025/8/22 17:00
 * @Author: guox
 * @File: TbVideoCategory
 * @Description: 视频所属类别
 */
@Entity
@Table(name = "tb_video_category")
public class TbVideoCategory {

    @Id
    @Column(name = "id", columnDefinition = "INT8 AUTO_INCREMENT")
    private Long id;


    @Column(name = "category")
    private String category;


    @Column(name = "deleted", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean deleted;
}
