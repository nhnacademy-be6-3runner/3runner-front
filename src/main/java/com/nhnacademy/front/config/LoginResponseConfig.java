package com.nhnacademy.front.config;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nhnacademy.front.threadlocal.TokenHolder;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoginResponseConfig {
	@Bean
	public Decoder decoder(ObjectFactory<HttpMessageConverters> messageConverters) {
		return new CustomDecoder(new SpringDecoder(messageConverters));
	}

	public static class CustomDecoder implements Decoder {
		private final Decoder decoder;

		public CustomDecoder(Decoder decoder) {
			this.decoder = decoder;
		}

		@Override
		public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
			Map<String, Collection<String>> headers = response.headers();

			// HTTP 헤더에 접근
			String customHeader = response.headers()
				.getOrDefault("Authorization", Collections.emptyList())
				.stream()
				.findFirst()
				.orElse(null);
			log.warn("Authorization header: {}", customHeader);

			// cookie 접근
			Collection<String> cookies = headers.getOrDefault("Set-Cookie", Collections.emptyList());
			for (String cookie : cookies) {
				if (cookie.startsWith("Refresh=")) {
					log.warn("Refresh cookies: {}", cookie);
					TokenHolder.setRefreshToken(cookie.substring("Refresh=".length()));
				}
			}

			TokenHolder.setAccessToken(customHeader.split(" ")[1]);

			// 기본 디코더로 응답 처리
			return decoder.decode(response, type);
		}
	}
}
