package com.nhnacademy.front.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomFeignException extends RuntimeException {
	public CustomFeignException(String message) {
		super(message);
	}
}
