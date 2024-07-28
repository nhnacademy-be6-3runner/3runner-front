package com.nhnacademy.front.config;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.front.util.ApiResponse;
import com.nhnacademy.global.exception.CustomFeignException;
import com.nhnacademy.global.exceptionHandler.BasicErrorResponse;
import com.nhnacademy.global.exceptionHandler.ErrorResponseForm;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Feign 에러 디코더
 *
 * 다른 서버에 페잉 요청 보낸 중 발생한 에러를 받아서,
 * CustomFeignException 에 담는다.
 *
 * @author 오연수
 */
@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

	private final ErrorDecoder defaultErrorDecoder = new Default();

	@Override
	public Exception decode(String methodKey, Response response) {
		ObjectMapper mapper = new ObjectMapper();
		String body = null;

		try {
			body = Util.toString(response.body().asReader());
			// log.warn("response body: {}", body);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// 다양한 타입으로 파싱 시도
		try {
			// 첫 번째 타입 시도: ApiResponse<ErrorResponseForm>
			TypeReference<ApiResponse<ErrorResponseForm>> typeRef1 = new TypeReference<>() {
			};
			ApiResponse<ErrorResponseForm> apiResponse1 = mapper.readValue(body, typeRef1);
			if (apiResponse1 != null) {
				return new CustomFeignException(apiResponse1);
			}
		} catch (IOException ignored) {
			// 파싱 실패 시 무시하고 다음 타입 시도
		}

		try {
			// 두 번째 타입 시도: 기본 에러 형식
			TypeReference<BasicErrorResponse> typeRef2 = new TypeReference<>() {
			};
			BasicErrorResponse basicErrorResponse = mapper.readValue(body, typeRef2);
			if (basicErrorResponse != null) {
				return new CustomFeignException(
					ApiResponse.fail(basicErrorResponse.getStatus(), new ApiResponse.Body<>(
						ErrorResponseForm.builder()
							.title(basicErrorResponse.getError())
							.status(basicErrorResponse.getStatus())
							.timestamp(basicErrorResponse.getTimestamp())
							.build()
					))
				);
			}
		} catch (IOException ignored) {
			// 파싱 실패 시 무시하고 다음 타입 시도
		}

		return new RuntimeException("에러 디코딩 중 문제 발생");
	}

}

