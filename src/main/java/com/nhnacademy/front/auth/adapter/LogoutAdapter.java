package com.nhnacademy.front.auth.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.nhnacademy.front.config.FeignLogoutConfig;

@FeignClient(value = "auth-logout-api", url = "${feign.client.url}", configuration = FeignLogoutConfig.class)
public interface LogoutAdapter {
	@PostMapping("/auth/logout")
	ResponseEntity<Void> logout();
}
