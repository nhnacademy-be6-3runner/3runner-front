package com.nhnacademy.front.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginException extends AuthenticationException {
	public LoginException(String message) {
		super(message);
	}
}
