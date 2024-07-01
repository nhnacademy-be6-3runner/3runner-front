package com.nhnacademy.global.exceptionHandler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BasicErrorResponse {
	private String error;
	private String path;
	private int status;
	private String timestamp;
}

