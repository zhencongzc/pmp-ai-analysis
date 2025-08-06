package com.pmp.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * Token工具类
 * 提供Token相关的通用操作方法
 */
@Slf4j
public class TokenUtils {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * 从HTTP请求中提取Bearer Token
     *
     * @param request HTTP请求对象
     * @return 提取到的Token字符串，如果未找到则返回null
     */
    public static String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    /**
     * 从Authorization头中提取原始Token值
     *
     * @param request HTTP请求对象
     * @return 完整的Authorization头值，如果未找到则返回null
     */
    public static String extractAuthorizationHeader(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    /**
     * 验证Token格式是否为Bearer Token
     *
     * @param token 完整的Authorization头值
     * @return 如果是Bearer Token格式返回true，否则返回false
     */
    public static boolean isBearerToken(String token) {
        return token != null && token.startsWith(BEARER_PREFIX);
    }

    /**
     * 从Bearer Token中提取实际的Token值
     *
     * @param bearerToken 完整的Bearer Token字符串
     * @return 实际的Token值
     */
    public static String extractBearerToken(String bearerToken) {
        if (isBearerToken(bearerToken)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return bearerToken;
    }
}