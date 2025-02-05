/**
 * @Time: 2025/2/5 12:51
 * @Author: guoxun
 * @File: TbAuthPermissions
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
@Table(name = "tb_auth_permission")
public class TbAuthPermission {

    @Id
    @TableId(type = IdType.AUTO)
    @Comment("权限主键")
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "INT8 AUTO_INCREMENT")
    private Long id;

    @Comment("权限名称")
    @Column(name = "name")
    private String name;

    @Comment("权限描述")
    @Column(name = "description")
    private String description;

    @Comment("删除位")
    @Column(name = "deleted", columnDefinition = "bool default 0")
    private Boolean deleted;
}
