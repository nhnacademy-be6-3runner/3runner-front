package com.nhnacademy.front.config;

import java.util.Objects;

import org.springframework.context.annotation.Bean;

import com.nhnacademy.front.threadlocal.TokenHolder;

import feign.RequestInterceptor;

public class FeignLogoutConfig {
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
