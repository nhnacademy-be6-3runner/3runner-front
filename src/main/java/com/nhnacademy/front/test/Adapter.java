package com.nhnacademy.front.test;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhnacademy.front.config.FeignRequestConfig;

// gateway port : 8080
@FeignClient(value = "bookstore-api", url = "${feign.client.url}", configuration = FeignRequestConfig.class)
public interface Adapter {
	@GetMapping("/bookstore/test")
	String getBook();

	@GetMapping("/coupon")
	String getCoupon();
}
