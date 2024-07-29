package com.nhnacademy.front.interceptor;

import com.nhnacademy.front.threadlocal.TokenHolder;
import com.nhnacademy.front.token.service.TokenService;
import com.nhnacademy.front.util.CookieUtil;
import com.nhnacademy.front.util.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 컨트롤러 실행 전 쿠키 유무 확인 후, 스레드 로컬에 토큰 값 설정하는 인터셉터
 *
 * @author 오연수
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CustomInterceptor implements HandlerInterceptor {

	private final JWTUtil jwtUtil;
	private final TokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		Optional<Cookie[]> cookies = Optional.ofNullable(request.getCookies());

		if (cookies.isPresent()) {
			for (Cookie cookie : cookies.orElse(null)) {
				if (cookie.getName().equals("Refresh")) {
					String refreshToken = cookie.getValue();
					log.warn("기존 쿠키 - refreshToken: {}", refreshToken);
					TokenHolder.setRefreshToken(refreshToken);
				}
				if (cookie.getName().equals("Access")) {
					String accessToken = cookie.getValue();
					log.warn("기존 쿠키 - accessToken: {}", accessToken);

					// Access token 만료된 경우
					if (jwtUtil.isExpired(accessToken)) {
						String refreshToken = findRefreshToken(request, response);
						log.error("기존 쿠키 - refresh token: {}", refreshToken);

						accessToken = tokenService.requestNewAccessToken(refreshToken);

						// 기존 cookie 삭제
						cookie.setMaxAge(0);
						response.addCookie(cookie);

						// 새로운 Access cookie 추가
						Cookie newAccessTokenCookie = CookieUtil.createCookie("Access", accessToken);
						response.addCookie(newAccessTokenCookie);
						log.error("재발급 쿠키에 설정 - access token: {}", accessToken);

						// 새로운 refresh cookie 추가
						Cookie newRefreshTokenCookie = CookieUtil.createCookie("Refresh", TokenHolder.getRefreshToken(),
							7 * 24 * 60 * 60);

						response.addCookie(newRefreshTokenCookie);
						log.error("재발급 쿠키에 설정 - refresh token: {}", TokenHolder.getRefreshToken());
					}

					TokenHolder.setAccessToken(accessToken);
					log.info("Interceptor, Access token 토큰 홀더에 세팅");
				}
			}
		}
		log.warn("Interceptor, Access Token 확인 {}", TokenHolder.getAccessToken());

		if (CorsUtils.isPreFlightRequest(request)) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, X-CSRF-TOKEN");
			response.setHeader("Access-Control-Allow-Credentials", "true");
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

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) {
		TokenHolder.resetAccessToken();
		TokenHolder.resetRefreshToken();
		log.info("Interceptor, Access token 토큰 홀더 리셋");
	}

	private String findRefreshToken(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
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
