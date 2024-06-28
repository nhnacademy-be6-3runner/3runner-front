package com.nhnacademy.front.auth.service;

import com.nhnacademy.front.auth.dto.request.LoginRequest;
import com.nhnacademy.front.auth.dto.response.LoginResponse;

public interface LoginService {

	LoginResponse getLoginResponse(LoginRequest loginRequest);

	boolean checkLoginStatus();
}
