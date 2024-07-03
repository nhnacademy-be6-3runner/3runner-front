package com.nhnacademy.front.auth.service.impl;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.nhnacademy.front.auth.adapter.LoginAdapter;
import com.nhnacademy.front.auth.adapter.LogoutAdapter;
import com.nhnacademy.front.auth.dto.request.LoginRequest;
import com.nhnacademy.front.auth.dto.response.LoginResponse;
import com.nhnacademy.front.auth.exception.LoginException;
import com.nhnacademy.front.auth.service.LoginService;
import com.nhnacademy.front.threadlocal.TokenHolder;
import com.nhnacademy.front.util.ApiResponse;

/**
 * 로그인 서비스 구현체
 *
 * @author 오연수
 */
@Service
public class LoginServiceImpl implements LoginService {
	private final LoginAdapter loginAdapter;
	private final LogoutAdapter logoutAdapter;

	public LoginServiceImpl(LoginAdapter loginAdapter, LogoutAdapter logoutAdapter) {
		this.loginAdapter = loginAdapter;
		this.logoutAdapter = logoutAdapter;
	}

	public LoginResponse getLoginResponse(LoginRequest loginRequest) {
		ApiResponse<LoginResponse> response = null;
		try {
			response = loginAdapter.login(loginRequest);
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		}
		return response.getBody().getData();
	}

	@Override
	public boolean checkLoginStatus() {
		// ThreadLocal 값 확인
		String accessToken = TokenHolder.getAccessToken();

		// TODO 백엔드로 보내서 체크하거나, 여기서 체크해도 될 듯

		if (Objects.isNull(accessToken) || accessToken.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public void logout() {
		logoutAdapter.logout();
	}
}
