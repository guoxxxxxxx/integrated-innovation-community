package com.iecas.communityuserservice.table;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Comment;

/**
 * @Time: 2025/8/22 20:57
 * @Author: guox
 * @File: TbUserFollow
 * @Description: 用户关注信息表
 */
@Entity
@TableName("tb_user_follow")
public class TbUserFollow {

    @Id
    @Column(name = "id", columnDefinition = "INT8 AUTO_INCREMENT")
    @Comment("主键")
    private Long id;

    @Comment("关注者id")
    @Column(name = "source")
    private Long source;

    @Comment("被关注者id")
    @Column(name = "target")
    private Long target;

    @Comment("关注状态")
    @Column(name = "status", columnDefinition = "TINYINT DEFAULT 1")
    private Boolean status;

    @Comment("删除位")
    @Column(name = "deleted", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean deleted;
}
