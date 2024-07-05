package com.nhnacademy.front.refund.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nhnacademy.front.util.ApiResponse;

@FeignClient(name = "refundControllerClient", url = "http://${feign.client.url}")
public interface RefundControllerClient {

	@GetMapping("/bookstore/refund/{purchaseId}")
	ApiResponse<String> readTossOrderId(@PathVariable("purchaseId") String purchaseId);

}
