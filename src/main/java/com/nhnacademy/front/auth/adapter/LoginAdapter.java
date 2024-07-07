package com.nhnacademy.front.auth.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.nhnacademy.front.auth.config.LoginResponseConfig;
import com.nhnacademy.front.auth.dto.request.LoginRequest;
import com.nhnacademy.front.auth.dto.response.LoginResponse;
import com.nhnacademy.front.util.ApiResponse;

@FeignClient(value = "bookstore-login-api", url = "${feign.client.url}", configuration = LoginResponseConfig.class)
public interface LoginAdapter {
	@PostMapping("/auth/login")
	ApiResponse<LoginResponse> login(LoginRequest loginRequest);

}
