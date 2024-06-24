package com.nhnacademy.front.auth.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.nhnacademy.front.auth.dto.request.LoginRequest;
import com.nhnacademy.front.auth.dto.response.LoginResponse;

@FeignClient(value = "bookstore-login-api", url = "http://localhost:8080")
public interface LoginAdapter {
	@PostMapping("/auth/login")
	ResponseEntity<LoginResponse> login(LoginRequest loginRequest);
}
