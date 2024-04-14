package com.example.demo.base;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BackendException extends RuntimeException {
    /**
     * token为空时的异常
     */
    public static final BackendException AUTH_EMPTY_TOKEN = new BackendException("token为空");
    /**
     * token过期或无效的异常
     */
    public static final BackendException AUTH_INVALID_TOKEN = new BackendException("token无效或过期");
    /**
     * 操作权限不满足的异常
     */
    public static final BackendException AUTH_INVALID_LEVEL = new BackendException("当前用户没有该操作的权限");
    /**
     * AdminUser异常
     */


    public static final BackendException ADMIN_NAME_EXISTED = new BackendException("该用户名已存在");
    public static final BackendException ADMIN_LEVEL_ROOT = new BackendException("权限等级不可设置为ROOT");
    public static final BackendException ADMIN_NOT_EXISTED = new BackendException("该用户不存在");
    public static final BackendException ADMIN_OLD_PASSWORD_ERROR = new BackendException("旧密码错误");
    public static final BackendException ADMIN_PASSWORD_ERROR = new BackendException("密码错误");

    /**
     * 异常常量
     */

    /**
     * 鉴权异常常量
     */
    public static final BackendException CODE_ERROR = new BackendException("验证码错误");
    public static final BackendException ADMIN_REMOVE_ROOT = new BackendException("根管理员不可移除");
    public static final BackendException ADMIN_LOG_NAME_ERROR = new BackendException("管理员操作名错误");
    /**
     * 接口异常
     */

    public static final BackendException API_DEPRECATED = new BackendException("该api已废弃");

    /**
     * controller参数异常
     */
    public static final BackendException ID_NULL = new BackendException("id 不能为空");
    public static final BackendException ID_ERROR = new BackendException("id 错误，不能查出相关实体");

    private final int code;

    public BackendException(String message) {
        super(message);
        this.code = -1;
//        logException(message);
    }

    public BackendException(String message, Throwable cause) {
        super(message, cause);
        this.code = -1;
//        logException(message);
    }

    public BackendException(int code) {
        this.code = code;
//        logException(String.valueOf(code));
    }

    public BackendException(String message, int code) {
        super(message);
        this.code = code;
        logException(message);
    }

    public BackendException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;

        logException(message);
    }

    private static void logException(String message) {
        log.warn("Backend Exception: {}", message);
    }

    public int getCode() {
        return code;
    }

}