package com.backend_pg.util;

import com.alibaba.fastjson.JSONObject;
import com.backend_pg.exception.ErrorCode;

public class ResponseBuilder {
    public static String simpleErrorResponse(ErrorCode code, String msg) {
        JSONObject obj = new JSONObject();
        obj.put("code", code.ordinal());
        obj.put("msg", msg);
        return obj.toJSONString();
    }
}
