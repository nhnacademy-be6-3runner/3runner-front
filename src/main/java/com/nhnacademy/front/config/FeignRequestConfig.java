package com.nhnacademy.front.config;

import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nhnacademy.front.threadlocal.TokenHolder;

import feign.RequestInterceptor;

/**
 * Feign 요청에 Authorization 헤더 추가
 * ThreadLocal 에 설정된 토큰 값 가져와서 설정
 *
 * @author 오연수
 */
@Configuration
public class FeignRequestConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			String accessToken = TokenHolder.getAccessToken();

			// Access Token 헤더에 추가
			if (Objects.nonNull(accessToken) && !accessToken.isEmpty()) {
				requestTemplate.header("Authorization", "Bearer " + accessToken);
			}
		};
	}
}
