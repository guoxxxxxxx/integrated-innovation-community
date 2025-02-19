package com.iecas.communitycommon.model.auth.entity;

import java.io.Serializable;
import java.io.Serial;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * (AuthRole)表实体类
 *
 * @author guox
 * @since 2025-02-05 20:07:55
 */
@Data
@AllArgsConstructor
public class AuthRole implements Serializable{
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    
    /**
     * 角色主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 删除位
     */
    private Integer deleted;
    
    /**
     * 角色描述
     */
    private String description;
    
    /**
     * 角色名称
     */
    private String name;
}

