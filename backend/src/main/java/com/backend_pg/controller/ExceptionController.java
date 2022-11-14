package com.backend_pg.controller;

import com.backend_pg.exception.ErrorCode;
import com.backend_pg.exception.RequestException;
import com.backend_pg.util.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionController {
    Logger logger = LoggerFactory.getLogger(ExceptionController.class);
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception ex, HttpServletResponse response) {
        if(ex instanceof RequestException re) {
            return ResponseBuilder.simpleErrorResponse(re.code, re.getMsg());
        } else if(ex instanceof HttpRequestMethodNotSupportedException re) {
            return ResponseBuilder.simpleErrorResponse(ErrorCode.REQUEST_METHOD_NOT_SUPPORTED, re.getMethod());
        } else if(ex instanceof MissingServletRequestParameterException re) {
            return ResponseBuilder.simpleErrorResponse(ErrorCode.PARAMETER_MISSING, re.getParameterName());
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("意料之外的异常", ex);
            return ResponseBuilder.simpleErrorResponse(ErrorCode.UNEXPECTED_EXCEPTION, String.format("%s : %s", ex.getClass().getSimpleName(), ex.getMessage()));
        }
    }
}
