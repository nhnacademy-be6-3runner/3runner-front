package com.nhnacademy.front.exceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nhnacademy.front.exception.CustomFeignException;
import com.nhnacademy.front.util.ApiResponse;

@RestControllerAdvice
public class WebControllerAdvice {

	@ExceptionHandler(RuntimeException.class)
	public ApiResponse<ErrorResponseForm> runtimeExceptionHandler(RuntimeException e) {
		return ApiResponse.fail(500,
			new ApiResponse.Body<>(
				ErrorResponseForm.builder()
					.title(e.getMessage())
					.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.timestamp(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toString())
					.build()
			));
	}

	@ExceptionHandler(CustomFeignException.class)
	public ApiResponse<ErrorResponseForm> customFeignExceptionHandler(CustomFeignException e) {
		return ApiResponse.fail(500,
			new ApiResponse.Body<>(
				ErrorResponseForm.builder()
					.title(e.getMessage())
					.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.timestamp(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toString())
					.build()
			));
	}

}
