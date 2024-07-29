package com.nhnacademy.front.auth.adapter;

import com.nhnacademy.front.config.FeignLogoutConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "auth-logout-api", url = "${feign.client.url}", configuration = FeignLogoutConfig.class)
public interface LogoutAdapter {

	@PostMapping("/auth/logout")
	ResponseEntity<Void> logout();
}
