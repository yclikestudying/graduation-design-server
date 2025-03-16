package com.project.common;

import lombok.Data;

@Data
public class BaseResponse<T> {
    // 响应码
    private Integer code;
    // 响应信息
    private String message;
    // 响应数据
    private T data;
    // 响应时间戳
    private Long timestamp;

    public BaseResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    // 请求成功或失败的共同部分
    public static <T> BaseResponse<T> build(Integer code, String message, T data) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(code);
        baseResponse.setMessage(message);
        baseResponse.setData(data);
        return baseResponse;
    }

    /**
     * 请求成功携带数据
     * @param data
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> success(T data) {
        return build(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 请求成功不携带数据
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> success() {
        return build(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 请求失败
     * @param code
     * @param message
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> fail(Integer code, String message) {
        return build(code, message, null);
    }

    /**
     * 请求失败
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> fail() {
        return build(CodeEnum.FAIL.getCode(), CodeEnum.FAIL.getMessage(),null);
    }
}
