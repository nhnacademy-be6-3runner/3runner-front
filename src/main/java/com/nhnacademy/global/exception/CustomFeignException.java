package com.nhnacademy.global.exception;

import com.nhnacademy.front.util.ApiResponse;

import lombok.Getter;

/**
 * 커스텀한 Feign Exception
 *
 * @author 오연수
 */
@Getter
public class CustomFeignException extends RuntimeException {
	/**
	 * 다른 서버에서 넘겨준 ApiResponse 를 담을 필드
	 */
	ApiResponse<?> apiResponse;

	public CustomFeignException(String message) {
		super(message);
	}

	public CustomFeignException(ApiResponse<?> apiResponse) {
		this.apiResponse = apiResponse;
	}
}
