package com.nhnacademy.front.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.front.exception.CustomFeignException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

	private final ErrorDecoder defaultErrorDecoder = new Default();

	@Override
	public Exception decode(String methodKey, Response response) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			// 응답 본문을 읽어 커스텀 예외의 필드로 설정
			ErrorResponse errorResponse = mapper.readValue(response.body().asInputStream(), ErrorResponse.class);
			return new CustomFeignException(
				errorResponse.getMessage()
			);
		} catch (Exception e) {
			return defaultErrorDecoder.decode(methodKey, response);
		}
	}

	// 에러 응답을 매핑할 클래스
	public static class ErrorResponse {
		private String message;
		private String error;
		private String path;

		// Getters and Setters
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}
	}
}

