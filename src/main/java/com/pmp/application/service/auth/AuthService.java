package com.pmp.application.service.auth;

import com.pmp.domain.model.auth.LoginResultDTO;
import com.pmp.domain.model.auth.UserDO;

import javax.naming.AuthenticationException;


public interface AuthService {

    /**
     * 用户登录认证
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    LoginResultDTO login(String username, String password) throws AuthenticationException;

    /**
     * 生成访问令牌
     *
     * @param user 用户信息
     * @return 访问令牌
     */
    String generateAccessToken(UserDO user);

    /**
     * 生成刷新令牌
     *
     * @param user 用户信息
     * @return 刷新令牌
     */
    String generateRefreshToken(UserDO user);

    /**
     * 验证令牌有效性
     *
     * @param token 令牌
     * @return 是否有效
     */
    boolean validateToken(String token);

    /**
     * 根据令牌获取用户信息
     *
     * @param token 令牌
     * @return 用户信息
     */
    UserDO getUserByToken(String token);

    /**
     * 用户登出
     *
     * @param token 用户当前的访问令牌
     * @return 是否登出成功
     */
    boolean logout(String token);

    /**
     * 检查令牌是否在黑名单中（已登出）
     *
     * @param token 待检查的JWT令牌
     * @return 如果令牌在黑名单中返回true，否则返回false
     */
    boolean isTokenBlacklisted(String token);
}
