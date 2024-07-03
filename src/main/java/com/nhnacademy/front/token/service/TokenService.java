package com.nhnacademy.front.token.service;

public interface TokenService {
	String requestNewAccessToken(String refreshToken);
}
