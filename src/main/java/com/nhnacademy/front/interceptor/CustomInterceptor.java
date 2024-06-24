package com.nhnacademy.front.interceptor;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.front.threadlocal.TokenHolder;

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
		Optional<Cookie[]> cookies = Optional.ofNullable(request.getCookies());
		if (cookies.isPresent()) {
			for (Cookie cookie : cookies.orElse(null)) {
				if (cookie.getName().equals("Access")) {
					TokenHolder.setAccessToken(cookie.getValue());
					log.info("Interceptor, Access token 토큰 홀더에 세팅");
					break;
				}
			}
		}

		// TODO Cookie 없으면, 에러 발생시킬 건지
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		TokenHolder.reset();
		log.info("Interceptor, Access token 토큰 홀더 리셋");
	}
}
