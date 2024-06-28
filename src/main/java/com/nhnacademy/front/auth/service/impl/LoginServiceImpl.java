package com.nhnacademy.front.auth.service.impl;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.nhnacademy.front.auth.adapter.LoginAdapter;
import com.nhnacademy.front.auth.dto.request.LoginRequest;
import com.nhnacademy.front.auth.dto.response.LoginResponse;
import com.nhnacademy.front.auth.service.LoginService;
import com.nhnacademy.front.threadlocal.TokenHolder;
import com.nhnacademy.front.util.ApiResponse;

@Service
public class LoginServiceImpl implements LoginService {
	private final LoginAdapter loginAdapter;

	public LoginServiceImpl(LoginAdapter loginAdapter) {
		this.loginAdapter = loginAdapter;
	}

	public LoginResponse getLoginResponse(LoginRequest loginRequest) {
		ApiResponse<LoginResponse> response = loginAdapter.login(loginRequest);
		return response.getBody().getData();
	}

	@Override
	public boolean checkLoginStatus() {
		// ThreadLocal 값 확인
		String accessToken = TokenHolder.getAccessToken();
		if (Objects.isNull(accessToken) || accessToken.isEmpty()) {
			return false;
		}
		return true;
	}
}
