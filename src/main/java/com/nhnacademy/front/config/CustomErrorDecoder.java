package com.nhnacademy.front.config;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.front.exception.CustomFeignException;
import com.nhnacademy.front.exceptionHandler.ErrorResponseForm;
import com.nhnacademy.front.util.ApiResponse;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

	private final ErrorDecoder defaultErrorDecoder = new Default();

	@Override
	public Exception decode(String methodKey, Response response) {
		ObjectMapper mapper = new ObjectMapper();

		try (InputStream responseBodyStream = response.body().asInputStream()) {
			TypeReference<ApiResponse<ErrorResponseForm>> typeReference = new TypeReference<>() {
			};

			ApiResponse<ErrorResponseForm> apiResponse = mapper.readValue(responseBodyStream,
				typeReference);

			if (apiResponse != null) {
				return new CustomFeignException(
					apiResponse
				);
			}
		} catch (IOException e) {
			return defaultErrorDecoder.decode(methodKey, response);
		}
		return new RuntimeException("에러 디코딩 중 문제 발생");
	}
}

