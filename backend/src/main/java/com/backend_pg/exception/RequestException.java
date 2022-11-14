package com.backend_pg.exception;

/**
 * 请求处理异常类，可在Controller任何位置抛出
 * 需要指明对应的错误类型
 * @see ErrorCode
 * */
public class RequestException extends RuntimeException {
    public final ErrorCode code;
    public final String extra;
    public RequestException(ErrorCode code, String extra) {
        super(statusMsg(code, extra));
        this.code = code;
        this.extra = extra;
    }

    public RequestException(ErrorCode code) {
        this(code, null);
    }


    public String getMsg() {
        return statusMsg(code, extra);
    }
    private static String statusMsg(ErrorCode code, String extra) {
        if(extra == null) return code.msg;
        return String.format("%s : %s", code.msg, extra);
    }
}
