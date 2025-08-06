package com.pmp.common.pojo;

import lombok.Getter;

/**
 * 响应状态码枚举类
 */
@Getter
public enum ResponseCode {
    // 成功
    SUCCESS(200, "请求成功"),

    // 客户端错误
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权/登录失效"),
    FORBIDDEN(403, "权限不足"),
    NOT_FOUND(404, "资源不存在"),

    // 服务端错误
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

    // 业务相关错误
    PARAMETER_ERROR(1000, "参数错误"),
    DATA_NOT_FOUND(1001, "数据不存在"),
    OPERATION_FAILED(1002, "操作失败");

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
