package com.iecas.communitycommon.model.auth.entity;

import java.io.Serializable;
import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * (AuthPermission)表实体类
 *
 * @author guox
 * @since 2025-02-05 20:01:19
 */
@Data
@AllArgsConstructor
public class AuthPermission implements Serializable{
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    
    /**
     * 权限主键
     */
    private Long id;
    
    /**
     * 删除位
     */
    private Integer deleted;

    /**
     * 权限名称
     */
    private String name;
    
    /**
     * 权限描述
     */
    private String description;
}

