package com.pmp.domain.auth;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息数据对象
 * 用于存储和管理用户基本信息
 */
@Data
public class UserDO {

    /**
     * 用户唯一标识ID
     */
    private Long id;

    /**
     * 用户名，用于登录系统
     */
    private String username;

    /**
     * 用户密码，经过加密存储
     */
    private String password;

    /**
     * 用户邮箱地址
     */
    private String email;

    /**
     * 用户手机号码
     */
    private String phone;

    /**
     * 用户真实姓名或昵称
     */
    private String fullName;

    /**
     * 用户状态
     * 1: 启用（默认值）
     * 0: 禁用
     */
    private Integer status = 1;

    /**
     * 用户创建时间
     */
    private LocalDateTime createTime;

    /**
     * 用户信息最后更新时间
     */
    private LocalDateTime updateTime;
}
