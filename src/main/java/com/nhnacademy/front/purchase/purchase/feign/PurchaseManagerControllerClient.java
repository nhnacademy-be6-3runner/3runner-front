package com.nhnacademy.front.purchase.purchase.feign;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseResponse;
import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "purchaseManagerControllerClient", url = "${feign.client.url}")
public interface PurchaseManagerControllerClient {
	@GetMapping("/bookstore/managers/purchases")
	ApiResponse<Page<ReadPurchaseResponse>> readPurchases(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(required = false) String sort
	);

	@PutMapping("/bookstore/managers/purchases/{purchaseId}")
	ApiResponse<Long> purchaseUpdate(
		@PathVariable(value = "purchaseId") String purchaseId,
		@RequestParam String purchaseStatus);
}
