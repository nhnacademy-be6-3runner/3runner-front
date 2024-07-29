package com.nhnacademy.front.auth.service.impl;

import com.nhnacademy.front.auth.adapter.LoginAdapter;
import com.nhnacademy.front.auth.adapter.LogoutAdapter;
import com.nhnacademy.front.auth.dto.request.LoginRequest;
import com.nhnacademy.front.auth.dto.response.LoginResponse;
import com.nhnacademy.front.auth.exception.LoginException;
import com.nhnacademy.front.auth.service.LoginService;
import com.nhnacademy.front.threadlocal.TokenHolder;
import com.nhnacademy.front.util.ApiResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 로그인 서비스 구현체.
 *
 * @author 오연수
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
	private final LoginAdapter loginAdapter;
	private final LogoutAdapter logoutAdapter;

	public LoginResponse getLoginResponse(LoginRequest loginRequest) {
		try {
			return loginAdapter.login(loginRequest).getBody().getData();
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		}
	}

	@Override
	public boolean checkLoginStatus() {
		String accessToken = TokenHolder.getAccessToken();

		return !Objects.isNull(accessToken) && !accessToken.isEmpty();
	}

	@Override
	public void logout() {
		logoutAdapter.logout();
	}
}
