/**
 * @Time: 2025/2/7 10:35
 * @Author: guoxun
 * @File: TbAuthRolePermission
 * @Description: 用户角色权限关联信息表
 */

package com.iecas.communityauth.table;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "tb_auth_role_permission")
public class TbAuthRolePermission {

    @Id
    @TableId(type = IdType.AUTO)
    @Comment("主键")
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "INT8 AUTO_INCREMENT")
    private Long id;

    @Comment("角色id")
    @Column(name = "role_id")
    private Long roleId;

    @Comment("权限id")
    @Column(name = "permission_id")
    private Long permissionId;
}
