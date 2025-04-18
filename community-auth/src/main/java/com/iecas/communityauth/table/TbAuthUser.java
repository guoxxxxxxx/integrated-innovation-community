/**
 * @Time: 2025/2/5 12:57
 * @Author: guoxun
 * @File: TbAuthUser
 * @Description:
 */

package com.iecas.communityauth.table;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Entity
@Table(name = "tb_auth_user")
public class TbAuthUser {

    @Comment("主键")
    @Id
    @TableId(type = IdType.AUTO, value = "id")
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "int8 AUTO_INCREMENT")
    private Long id;

    @Comment("用户邮箱")
    @Column(name = "email", unique = true)
    private String email;

    @Comment("用户手机号")
    @Column(name = "phone", unique = true)
    private String phone;

    @Comment("密码")
    @Column(name = "password")
    private String password;

    @Comment("角色")
    @Column(name = "role_id", columnDefinition = "INT8 DEFAULT 3")
    private Long roleId;

    @Comment("账户是否没过期")
    @Column(name = "accountNonExpired", columnDefinition = "bool default 1")
    private Boolean accountNonExpired;

    @Comment("账户是否没被锁定")
    @Column(name = "accountNonLocked", columnDefinition = "bool default 1")
    private Boolean accountNonLocked;

    @Comment("资格是否过期")
    @Column(name = "credentialsNonExpired", columnDefinition = "bool default 1")
    private Boolean credentialsNonExpired;

    @Comment("是否可用")
    @Column(name = "enabled", columnDefinition = "bool default 1")
    private Boolean enabled;

    @Comment("上次登录时间")
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Comment("上次登录ip")
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Comment("删除位")
    @Column(name = "deleted", columnDefinition = "bool default 0")
    private Boolean deleted;
}
