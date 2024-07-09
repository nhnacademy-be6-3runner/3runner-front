package com.nhnacademy.bookstore.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class CustomInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (CorsUtils.isPreFlightRequest(request)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        if (CorsUtils.isCorsRequest(request)) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, X-CSRF-TOKEN");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            return true;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
