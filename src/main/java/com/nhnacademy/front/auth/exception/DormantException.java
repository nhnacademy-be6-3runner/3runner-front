package com.nhnacademy.front.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class DormantException extends AuthenticationException {
	public DormantException(String msg) {
		super(msg);
	}
}



