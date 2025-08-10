/**
 * @Time: 2025/2/5 19:11
 * @Author: guoxun
 * @File: TbUserInfo
 * @Description:
 */

package com.iecas.communityuserservice.table;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

import java.util.Date;


@Entity
@Table(name = "tb_user_info")
public class TbUserInfo {

    @Comment("主键")
    @Id
    @TableId(type = IdType.AUTO)
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "INT8 AUTO_INCREMENT")
    private Long id;

    @Comment("用户权限关联id")
    @Column(name = "auth_user_id", unique = true, columnDefinition = "INT8")
    private Long authUserId;

    @Comment("用户昵称")
    @Column(name = "nickname", length = 64)
    private String nickname;

    @Comment("邮箱")
    @Column(name = "email", unique = true)
    private String email;

    @Comment("手机号")
    @Column(name = "phone", unique = true)
    private String phone;

    @Comment("头像路径")
    @Column(name = "avatar")
    private String avatar;

    @Comment("性别")
    @Column(name = "gender", length = 8)
    private String gender;

    @Comment("注册时间")
    @Column(name = "register_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date registerTime;

    @Comment("生日")
    @Column(name = "birthday")
    private Date birthday;
}
