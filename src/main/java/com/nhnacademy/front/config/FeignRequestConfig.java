package com.nhnacademy.front.config;

import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nhnacademy.front.threadlocal.TokenHolder;
import com.nhnacademy.front.token.service.TokenService;
import com.nhnacademy.front.util.JWTUtil;

import feign.RequestInterceptor;

/**
 * Feign 요청에 Authorization 헤더 추가
 * ThreadLocal 에 설정된 토큰 값 가져와서 설정
 *
 * @author 오연수
 */
@Configuration
public class FeignRequestConfig {
	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private TokenService tokenService;

	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			String accessToken = TokenHolder.getAccessToken();

			// Access Token 검증, 만료 되었으면 REFRESH TOKEN 전송
			if (jwtUtil.isExpired(accessToken)) {
				Collection<String> cookies = requestTemplate.headers().get("Set-Cookie");
				// String refreshToken = TokenHolder.getRefreshToken();
				String refreshToken = null;
				if (cookies != null) {
					for (String cookie : cookies) {
						if (cookie.startsWith("Refresh=")) {
							refreshToken = cookie.substring("Refresh=".length());
							break;
						}
					}
				}
				accessToken = tokenService.requestNewAccessToken(refreshToken);
			}

			// Access Token 헤더에 추가
			if (Objects.nonNull(accessToken) && !accessToken.isEmpty()) {
				requestTemplate.header("Authorization", "Bearer " + accessToken);
			}
		};
	}
}
