package com.pmp.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 解决前端跨域问题
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        // 1. 配置跨域信息
        CorsConfiguration config = new CorsConfiguration();
        // 允许的来源（*表示允许所有，生产环境建议指定具体域名，如"http://localhost:8080"）
        config.addAllowedOrigin("*");
        // 允许的请求头（*表示允许所有，如Content-Type、Authorization等）
        config.addAllowedHeader("*");
        // 允许的请求方法（GET、POST、PUT等）
        config.addAllowedMethod("*");
        // 允许携带Cookie（跨域请求默认不携带Cookie，如需开启需设为true）
        config.setAllowCredentials(true);
        // 预检请求的有效期（单位：秒，避免频繁发送预检请求）
        config.setMaxAge(3600L);

        // 2. 配置生效的接口路径（/**表示所有接口）
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}