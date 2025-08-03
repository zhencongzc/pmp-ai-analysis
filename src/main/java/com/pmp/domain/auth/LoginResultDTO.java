package com.pmp.domain.auth;

import lombok.Data;

@Data
public class LoginResultDTO {
    /**
     * 访问令牌
     */
    private String token;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 用户信息
     */
//    private UserInfo userInfo;
}
