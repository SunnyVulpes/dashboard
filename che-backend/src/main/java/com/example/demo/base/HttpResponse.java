package com.example.demo.base;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public final class HttpResponse<T> {
    private static final int SUCCESS_CODE = 200;
    private static final int ERROR_CODE = -1;
    private int code;
    private T data;
    private String message;

    private HttpResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static HttpResponse<Void> success() {
        return new HttpResponse<>(SUCCESS_CODE, null, "success");
    }

    public static HttpResponse<Void> successWithMessage(String message) {
        return new HttpResponse<>(SUCCESS_CODE, null, message);
    }

    public static <T> HttpResponse<T> success(T data) {
        return new HttpResponse<>(SUCCESS_CODE, data, "success");
    }

    public static <T> HttpResponse<T> successWithMessage(T data, String message) {
        return new HttpResponse<>(SUCCESS_CODE, data, message);
    }

    public static HttpResponse<Void> error() {
        return new HttpResponse<>(ERROR_CODE, null, "error");
    }

    public static HttpResponse<Void> errorWithMessage(String message) {
        return new HttpResponse<>(ERROR_CODE, null, message);
    }

    public static HttpResponse<Void> error(int code) {
        return new HttpResponse<>(code, null, "error");
    }

    public static HttpResponse<Void> errorWithMessage(int code, String message) {
        return new HttpResponse<>(code, null, message);
    }

    public String toJsonString() {
        return JSONObject.toJSONString(this);
    }
}
