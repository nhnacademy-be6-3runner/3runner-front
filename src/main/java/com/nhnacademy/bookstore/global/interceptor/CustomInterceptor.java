package com.nhnacademy.bookstore.global.interceptor;

import java.util.Optional;

import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 컨트롤러 실행 전 쿠키 유무 확인 후, 스레드 로컬에 토큰 값 설정하는 인터셉터
 *
 * @author 오연수
 */
@Component
@Slf4j
public class CustomInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {

		if (CorsUtils.isPreFlightRequest(request)) {
			response.setStatus(HttpServletResponse.SC_OK);
//			response.setHeader("Access-Control-Allow-Origin", "*");
//			response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, X-CSRF-TOKEN");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			return true;
		}
		if (CorsUtils.isCorsRequest(request)) {
//			response.setHeader("Access-Control-Allow-Origin", "*");
//			response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, X-CSRF-TOKEN");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			return true;
		}

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
