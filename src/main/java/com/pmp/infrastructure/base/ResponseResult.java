package com.pmp.infrastructure.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用API响应返回类型（支持分页或带计数的数据）
 *
 * @param <T> 数据部分的泛型类型
 */
@Data
public class ResponseResult<T> implements Serializable {

    /**
     * 状态码
     */
    private int code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应数据：泛型类型，可根据实际需求返回任意数据结构
     */
    private T data;

    /**
     * 数据总数：适用于分页或统计场景
     */
    private long count;

    // 私有构造方法，禁止直接实例化，需通过静态方法创建
    private ResponseResult() {
    }

    private ResponseResult(int code, String message, T data, long count) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.count = count;
    }

    // -------------------- 静态工厂方法：快捷创建响应对象 --------------------

    /**
     * 成功响应（无数据，无计数）
     */
    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), null, 0);
    }

    /**
     * 成功响应（带数据，无计数）
     *
     * @param data 响应数据
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data, 0);
    }

    /**
     * 成功响应（带数据和计数，适用于分页）
     *
     * @param data  响应数据（通常为列表）
     * @param count 数据总数
     */
    public static <T> ResponseResult<T> success(T data, long count) {
        return new ResponseResult<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data, count);
    }

    /**
     * 成功响应（自定义消息+数据+计数）
     *
     * @param message 自定义成功消息
     * @param data    响应数据
     * @param count   数据总数
     */
    public static <T> ResponseResult<T> success(String message, T data, long count) {
        return new ResponseResult<>(ResponseCode.SUCCESS.getCode(), message, data, count);
    }

    /**
     * 错误响应（自定义错误码+消息，无数据，无计数）
     *
     * @param code    错误码（非0）
     * @param message 错误描述
     */
    public static <T> ResponseResult<T> error(int code, String message) {
        return new ResponseResult<>(code, message, null, 0);
    }

}