package com.nhnacademy.front.token.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.front.auth.config.LoginResponseConfig;
import com.nhnacademy.front.token.dto.request.RefreshRequest;
import com.nhnacademy.front.token.dto.response.RefreshResponse;
import com.nhnacademy.front.util.ApiResponse;

@FeignClient(url = "http://${feign.client.url}/auth", name = "TokenAdapter", configuration = LoginResponseConfig.class)
public interface TokenAdapter {
	@PostMapping("/reissue")
	ApiResponse<RefreshResponse> requestNewAccessToken(@RequestBody RefreshRequest refreshRequest);
}