package com.backend_pg.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 实现对所有请求进行log
 * */
@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        logger.info("[{}] {}", request.getMethod(), request.getRequestURI());
        return true;
    }
}
