package com.nhnacademy.front.config;

import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nhnacademy.front.threadlocal.TokenHolder;

import feign.RequestInterceptor;

/**
 * Feign 요청 보낼 때 스레드 로컬에서 토큰 값을 가져와 헤더로 보내는 Interceptor 설정
 *
 * @author 오연수
 */
@Configuration
public class FeignConfig {
	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			String token = TokenHolder.getAccessToken();
			if (Objects.nonNull(token) && !token.isEmpty()) {
				requestTemplate.header("Authorization", "Bearer " + token);
			}
		};
	}
}
