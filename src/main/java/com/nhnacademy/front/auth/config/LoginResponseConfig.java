package com.nhnacademy.front.auth.config;

import com.nhnacademy.front.threadlocal.TokenHolder;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;

/**
 * Login 요청 Feign 관련 Decoder 설정
 * LoginAdapter config 설정
 *
 * @author 오연수
 */
@Slf4j
public class LoginResponseConfig {

	@Bean
	public Decoder decoder(ObjectFactory<HttpMessageConverters> messageConverters) {
		return new CustomDecoder(new SpringDecoder(messageConverters));
	}

	/**
	 * 페잉 응답에서
	 * 1. Authorization header 읽고 스레드 로컬 Access Token 설정
	 * 2. Cookie 읽고 스레드 로컬 Refresh Token 설정
	 *
	 * @author 오연수
	 */
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
					String[] parts = cookie.split(";");
					for (String part : parts) {
						if (part.startsWith("Refresh=")) {
							part = part.trim();
							TokenHolder.setRefreshToken(part.substring("Refresh=".length()));
							log.warn("Refresh token: {}", part);
						}
					}
				}
			}

			assert customHeader != null;
			TokenHolder.setAccessToken(customHeader.split(" ")[1]);

			// 기본 디코더로 응답 처리
			return decoder.decode(response, type);
		}
	}
}
