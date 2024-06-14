package com.nhnacademy.front.test;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

// gateway port : 8080
@FeignClient(value = "bookstore-api", url = "http://localhost:8080")
public interface Adapter {
    @GetMapping("/bookstore")
    String getBook();

    @GetMapping("/coupon")
    String getCoupon();
}
