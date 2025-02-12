package com.iecas.communitycommon.aop.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 鉴权注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {


    String value() default "";

    /**
     * 允许的角色
     */
    String[] permitRole() default {};

    /**
     * 需要的权限
     */
    String[] needPermissions() default {};
}
