package com.nhnacademy.front.auth.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.front.auth.config.LoginResponseConfig;
import com.nhnacademy.front.auth.dto.request.DormantRequest;
import com.nhnacademy.front.auth.dto.request.LoginRequest;
import com.nhnacademy.front.auth.dto.response.LoginResponse;
import com.nhnacademy.front.member.member.dto.response.DormantResponse;
import com.nhnacademy.front.util.ApiResponse;
@FeignClient(value = "dormant-api",url = "http://${feign.client.url}")
public interface DormantAdapter {
	@PostMapping("/auth/dormant/resend")
	ApiResponse<Void> resendDormant(@RequestBody String email);

	@PostMapping("/auth/dormant")
	ApiResponse<DormantResponse> dormantCheck(@RequestBody DormantRequest loginRequest);

}

