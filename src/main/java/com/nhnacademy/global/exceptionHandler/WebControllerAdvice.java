package com.nhnacademy.global.exceptionHandler;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nhnacademy.front.auth.exception.LoginException;
import com.nhnacademy.front.util.ApiResponse;
import com.nhnacademy.global.exception.CustomFeignException;

import jakarta.servlet.http.HttpServletResponse;

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
	public ApiResponse<?> customFeignExceptionHandler(CustomFeignException e) {
		return e.getApiResponse();
	}

	// 예외 처리 메서드
	@ExceptionHandler(LoginException.class)
	public void handleLoginException(LoginException ex, HttpServletResponse response) throws IOException {
		// 리다이렉트할 URL 설정
		String redirectUrl = "/login?error=true";
		response.sendRedirect(redirectUrl);
	}

}
