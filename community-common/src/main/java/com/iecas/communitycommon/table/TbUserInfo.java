/**
 * @Time: 2025/2/4 18:29
 * @Author: guoxun
 * @File: TbUserInfo
 * @Description:
 */

package com.iecas.communitycommon.table;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

import java.sql.Date;

@Entity
@Table(name = "tb_user_info")
public class TbUserInfo {


    @Id
    @TableId(type = IdType.AUTO)
    @Comment("主键")
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "INT8 AUTO_INCREMENT")
    private Long id;

    @Comment("用户昵称")
    @Column(name = "nickname", length = 20)
    private String nickname;

    @Comment("用户邮箱")
    @Column(name = "email", length = 150)
    private String email;

    @Comment("密码")
    @Column(name = "password", length = 256, nullable = false)
    private String password;

    @Comment("性别")
    @Column(name = "sex", length = 2)
    private Integer sex;

    @Comment("加入时间")
    @Column(name = "join_time")
    private Date joinTime;

    @Comment("最后一次登录时间")
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Comment("最后一次登录ip")
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Comment("删除位")
    @Column(name = "deleted", columnDefinition = "BOOL DEFAULT false")
    private Boolean deleted;
}
