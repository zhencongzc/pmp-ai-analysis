package com.pmp.domain.model.auth;

import java.lang.annotation.*;

/**
 * 角色权限注解
 * 用于声明访问某个方法或类需要具备的角色权限
 * 支持在方法级别和类级别使用
 *
 * @author zhenc
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresRoles {
    /**
     * 需要的角色列表
     * 可以指定一个或多个角色编码
     * 例如: @RequiresRoles("admin") 或 @RequiresRoles({"admin", "user"})
     *
     * @return 角色编码数组
     */
    String[] value();
}