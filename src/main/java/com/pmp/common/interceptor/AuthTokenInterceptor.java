package com.pmp.common.interceptor;

import com.pmp.common.util.TokenUtils;
import com.pmp.application.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthTokenInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Value("${isDev}")
    private boolean isDev;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 开发环境关掉拦截器，方便测试
        if (isDev) {
            return true;
        }

        // 处理跨域预检请求(OPTIONS)，直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // 提取token
        String token = TokenUtils.extractAuthorizationHeader(request);
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"error\":\"Missing authorization token\",\"message\":\"请求缺少认证令牌\"}");
            return false;
        }

        // 检查token是否有效且不在黑名单中
        if (!authService.validateToken(token) || authService.isTokenBlacklisted(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"error\":\"Invalid or expired token\",\"message\":\"令牌无效或已过期\"}");
            return false;
        }

        return true;
    }

}