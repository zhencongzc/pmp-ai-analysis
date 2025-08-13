package com.pmp.application.service.auth.impl;

import com.pmp.domain.model.auth.LoginResultDTO;
import com.pmp.domain.model.auth.UserDO;
import com.pmp.infrastructure.mapper.AuthMapper;
import com.pmp.application.service.auth.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

    // 访问令牌过期时间（毫秒）- 120分钟
    private static final long ACCESS_TOKEN_EXPIRATION = 120 * 60 * 1000;
    // 刷新令牌过期时间（毫秒）- 7天
    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000;

    private final AuthMapper authMapper;

    // JWT签名密钥
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    // 黑名单存储（实际项目中建议使用Redis）
    private Set<String> tokenBlacklist = ConcurrentHashMap.newKeySet();

    /**
     * 用户登录认证
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @Override
    public LoginResultDTO login(String username, String password) throws AuthenticationException {
        // 查询用户信息
        UserDO user = authMapper.findByUsername(username);
        if (user == null) {
            throw new AuthenticationException("用户不存在");
        }

        // 检查用户状态
        if (user.getStatus() != 1) {
            throw new AuthenticationException("用户已被禁用");
        }

        // 验证密码
        if (!this.validatePassword(password, user.getPassword())) {
            throw new AuthenticationException("密码错误");
        }

        // 生成令牌
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);

        // 构造返回结果
        LoginResultDTO result = new LoginResultDTO();
        result.setToken(accessToken);
        result.setRefreshToken(refreshToken);
        result.setExpireTime(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION);

        return result;
    }

    /**
     * 生成用户访问令牌
     *
     * @param user 用户信息对象，包含用户ID、用户名、邮箱等信息
     * @return 返回生成的JWT访问令牌字符串
     */
    @Override
    public String generateAccessToken(UserDO user) {
        // 设置令牌过期时间
        Date expirationDate = new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION);

        // 创建令牌载荷
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());

        // 生成JWT令牌
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    /**
     * 生成用户刷新令牌
     *
     * @param user 用户信息对象，包含用户ID和用户名等信息
     * @return 返回生成的JWT刷新令牌字符串
     */
    @Override
    public String generateRefreshToken(UserDO user) {
        // 设置刷新令牌过期时间
        Date expirationDate = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION);

        // 创建刷新令牌载荷
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        claims.put("tokenType", "refresh");

        // 生成刷新令牌
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    /**
     * 验证JWT令牌的有效性
     *
     * @param token 待验证的JWT令牌字符串
     * @return 验证成功返回true，验证失败返回false
     */
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            logger.warning("令牌验证失败: " + e.getMessage());
            return false;
        }
    }

    /**
     * 根据JWT令牌获取用户信息
     *
     * @param token JWT令牌字符串
     * @return UserDO 用户信息对象，如果解析失败则返回null
     */
    @Override
    public UserDO getUserByToken(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Long userId = Long.valueOf(claims.get("userId").toString());
            return authMapper.findUserById(userId);
        } catch (Exception e) {
            logger.warning("通过令牌获取用户信息失败: " + e.getMessage());
            return null;
        }
    }

    /**
     * 用户登出
     *
     * @param token 用户当前的访问令牌
     * @return 是否登出成功
     */
    @Override
    public boolean logout(String token) {
        try {
            // 将token加入黑名单
            if (validateToken(token)) {
                tokenBlacklist.add(token);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.warning("登出失败: " + e.getMessage());
            return false;
        }
    }

    /**
     * 检查token是否在黑名单中
     *
     * @param token 待检查的令牌
     * @return 是否在黑名单中
     */
    @Override
    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }

    /**
     * 验证密码
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    private boolean validatePassword(String rawPassword, String encodedPassword) {
        // 这里应该使用BCrypt或其他加密算法进行密码比对
        // 示例中简化处理
        return rawPassword.equals(encodedPassword); // 实际项目中不应这样处理
    }
}
