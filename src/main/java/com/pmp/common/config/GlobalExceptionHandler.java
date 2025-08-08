package com.pmp.common.config;

import com.pmp.common.exception.AccessDeniedException;
import com.pmp.common.pojo.ResponseCode;
import com.pmp.common.pojo.ResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult<String> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseResult.error(ResponseCode.FORBIDDEN.getCode(), e.getMessage());
    }
}