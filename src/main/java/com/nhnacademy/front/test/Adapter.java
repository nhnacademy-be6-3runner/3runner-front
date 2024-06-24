package com.nhnacademy.front.test;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhnacademy.front.config.FeignConfig;

// gateway port : 8080
@FeignClient(value = "bookstore-api", url = "http://localhost:8080", configuration = FeignConfig.class)
public interface Adapter {
	@GetMapping("/bookstore/test")
	String getBook();

	@GetMapping("/coupon")
	String getCoupon();
}
