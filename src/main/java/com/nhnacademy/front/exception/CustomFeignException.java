package com.nhnacademy.front.exception;

import com.nhnacademy.front.util.ApiResponse;

import lombok.Getter;

@Getter
public class CustomFeignException extends RuntimeException {
	ApiResponse<?> apiResponse;

	public CustomFeignException(String message) {
		super(message);
	}

	public CustomFeignException(ApiResponse<?> apiResponse) {
		this.apiResponse = apiResponse;
	}
}
