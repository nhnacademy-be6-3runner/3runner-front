package com.nhnacademy.front.purchase.mypage.feign;

import com.nhnacademy.front.purchase.mypage.dto.response.ReadPurchaseCouponDetailResponse;
import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PurchaseCouponControllerClient", url = "${feign.client.url}")
public interface PurchaseCouponControllerClient {
	@GetMapping("/bookstore/purchases/coupons")
	ApiResponse<Page<ReadPurchaseCouponDetailResponse>> readPurchaseCoupons(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size);
}
