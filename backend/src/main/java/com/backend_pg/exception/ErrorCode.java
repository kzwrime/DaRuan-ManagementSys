package com.backend_pg.exception;

/**
 * 错误代码及描述
 * */
public enum ErrorCode {
    // 在此处添加错误代码及其描述
    BAD_CREDENTIALS("密码错误"),
    USER_NOT_FOUND("用户不存在"),

    USERNAME_EXISTS("用户名已存在"),
    REQUEST_METHOD_NOT_SUPPORTED("指定的请求方式不受支持"),
    PARAMETER_MISSING("缺少参数"),
    UNEXPECTED_EXCEPTION("意料之外的错误");

    public final String msg;

    ErrorCode(String msg) {
        this.msg = msg;
    }
}
