package com.nhnacademy.front.auth.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.member.address.dto.request.LoginRequest;
import com.nhnacademy.util.ApiResponse;

import jakarta.validation.Valid;

@FeignClient(value = "bookstore-oauth-api", url = "http://localhost:8080")
public interface AuthAdapter {
	@PutMapping("/auth/login")
	ApiResponse<Void> loginMember(@RequestBody @Valid LoginRequest loginRequest);
	//이거 고쳐야함
	@PostMapping("/auth/oauth2/callback")
	ApiResponse<Void> handleOAuth2Redirect(@RequestBody String code);
}
