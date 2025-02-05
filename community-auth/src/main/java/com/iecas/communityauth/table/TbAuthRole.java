/**
 * @Time: 2025/2/5 12:43
 * @Author: guoxun
 * @File: TbAuthRoles
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


@Entity
@Table(name = "tb_auth_role")
public class TbAuthRole {

    @Comment("角色主键")
    @Id
    @TableId(type = IdType.AUTO)
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "INT8 AUTO_INCREMENT")
    private Long id;

    @Comment("角色名称")
    @Column(name = "name", length = 32)
    private String name;

    @Comment("角色描述")
    @Column(name = "description", length = 512)
    private String description;

    @Comment("删除位")
    @Column(name = "deleted", columnDefinition = "bool default 0")
    private Boolean deleted;
}
