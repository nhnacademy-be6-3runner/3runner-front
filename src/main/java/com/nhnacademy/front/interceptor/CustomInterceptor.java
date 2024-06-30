package com.nhnacademy.front.interceptor;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.front.threadlocal.TokenHolder;
import com.nhnacademy.front.token.service.TokenService;
import com.nhnacademy.front.util.JWTUtil;

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

	private JWTUtil jwtUtil;
	private TokenService tokenService;

	public CustomInterceptor(JWTUtil jwtUtil, TokenService tokenService) {
		this.jwtUtil = jwtUtil;
		this.tokenService = tokenService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		Optional<Cookie[]> cookies = Optional.ofNullable(request.getCookies());
		if (cookies.isPresent()) {
			for (Cookie cookie : cookies.orElse(null)) {
				if (cookie.getName().equals("Access")) {
					String accessToken = cookie.getValue();

					// Access token 만료된 경우
					if (jwtUtil.isExpired(accessToken)) {
						String refreshToken = findRefreshToken(request, response);

						accessToken = tokenService.requestNewAccessToken(refreshToken);

						// 기존 cookie 삭제
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}

					TokenHolder.setAccessToken(accessToken);
					log.info("Interceptor, Access token 토큰 홀더에 세팅");
					break;
				}
			}
		}

		log.warn("Interceptor, Access Token 확인 {}", TokenHolder.getAccessToken());
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		TokenHolder.resetAccessToken();
		log.info("Interceptor, Access token 토큰 홀더 리셋");
	}

	private String findRefreshToken(HttpServletRequest request, HttpServletResponse response) {
		// Access Token 검증, 만료 되었으면 REFRESH TOKEN 전송
		Cookie[] cookies = request.getCookies();
		// String refreshToken = TokenHolder.getRefreshToken();
		String refreshToken = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("Refresh")) {
					refreshToken = cookie.getValue();
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					return refreshToken;
				}
			}
		}
		return refreshToken;
	}
}
