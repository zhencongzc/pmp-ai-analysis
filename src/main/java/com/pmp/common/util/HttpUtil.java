
package com.pmp.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * HTTP请求工具类
 */
@Slf4j
@Component
public class HttpUtil {
    
    private static final RestTemplate restTemplate = new RestTemplate();
    
    /**
     * 发送POST请求
     * 
     * @param url 请求URL
     * @param requestBody 请求体内容
     * @param responseType 响应类型
     * @return 响应结果
     */
    public static <T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
            
            log.info("发送POST请求: URL={}, 请求体={}", url, requestBody);
            ResponseEntity<T> response = restTemplate.postForEntity(url, entity, responseType);
            log.info("收到响应: 状态码={}, 响应体={}", response.getStatusCode(), response.getBody());
            
            return response;
        } catch (RestClientException e) {
            log.error("发送POST请求失败: URL={}, 错误={}", url, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 发送POST请求（简化版）
     * 
     * @param url 请求URL
     * @param requestBody 请求体内容
     * @return 响应结果字符串
     */
    public static String post(String url, Object requestBody) {
        ResponseEntity<String> response = post(url, requestBody, String.class);
        return response.getBody();
    }
    
    /**
     * 发送GET请求
     * 
     * @param url 请求URL
     * @param responseType 响应类型
     * @return 响应结果
     */
    public static <T> ResponseEntity<T> get(String url, Class<T> responseType) {
        try {
            log.info("发送GET请求: URL={}", url);
            ResponseEntity<T> response = restTemplate.getForEntity(url, responseType);
            log.info("收到响应: 状态码={}, 响应体={}", response.getStatusCode(), response.getBody());
            
            return response;
        } catch (RestClientException e) {
            log.error("发送GET请求失败: URL={}, 错误={}", url, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 发送GET请求（带参数）
     * 
     * @param url 请求URL
     * @param params 请求参数
     * @param responseType 响应类型
     * @return 响应结果
     */
    public static <T> ResponseEntity<T> get(String url, Map<String, Object> params, Class<T> responseType) {
        try {
            // 构建带参数的URL
            StringBuilder urlBuilder = new StringBuilder(url);
            if (!params.isEmpty()) {
                urlBuilder.append("?");
                params.forEach((key, value) -> {
                    urlBuilder.append(key).append("=").append(value).append("&");
                });
                // 移除最后一个&
                urlBuilder.deleteCharAt(urlBuilder.length() - 1);
            }
            
            log.info("发送GET请求: URL={}", urlBuilder.toString());
            ResponseEntity<T> response = restTemplate.getForEntity(urlBuilder.toString(), responseType);
            log.info("收到响应: 状态码={}, 响应体={}", response.getStatusCode(), response.getBody());
            
            return response;
        } catch (RestClientException e) {
            log.error("发送GET请求失败: URL={}, 错误={}", url, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 发送GET请求（简化版）
     * 
     * @param url 请求URL
     * @return 响应结果字符串
     */
    public static String get(String url) {
        ResponseEntity<String> response = get(url, String.class);
        return response.getBody();
    }
}