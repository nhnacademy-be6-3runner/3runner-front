package com.nhnacademy.front.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nhnacademy.front.interceptor.CustomInterceptor;

/**
 * 인터셉터 설정하는 Config
 *
 * @author 오연수
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	private final CustomInterceptor customInterceptor;

	public InterceptorConfig(CustomInterceptor customInterceptor) {
		this.customInterceptor = customInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(customInterceptor);
	}
}
