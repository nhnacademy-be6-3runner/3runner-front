package com.nhnacademy.front.config;

import java.util.Objects;

import org.springframework.context.annotation.Bean;

import com.nhnacademy.front.threadlocal.TokenHolder;

import feign.RequestInterceptor;

/**
 * 페잉 요청 로그아웃 어댑터 설정
 *
 * @author 오연수
 */
public class FeignLogoutConfig {
	/**
	 * 해당 설정을 적용시킨 페잉 요청을 잡아
	 * Refresh Token 을 담는다.
	 *
	 * @return the request interceptor
	 */
	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			String refreshToken = TokenHolder.getRefreshToken();

			// cookie 추가
			if (Objects.nonNull(refreshToken)) {
				requestTemplate.header("Cookie", "Refresh=" + refreshToken);
			}
		};
	}
}
