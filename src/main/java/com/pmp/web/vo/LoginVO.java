package com.pmp.web.vo;

import lombok.Data;

/**
 * 登录参数VO类
 * 用于接收用户登录请求参数
 */
@Data
public class LoginVO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码（可选）
     */
    private String captcha;

    /**
     * 记住我功能
     */
    private Boolean rememberMe;

    /**
     * 登录方式（用户名/手机号/邮箱等）
     */
    private String loginType;
}