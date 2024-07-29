package com.nhnacademy.front.purchase.purchase.feign;

import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponFormResponse;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadPurchaseCouponResponse;
import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "CouponControllerClient", url = "${feign.client.url}")
public interface CouponControllerClient {

	@GetMapping("/bookstore/purchases/{purchaseId}/coupons")
	ApiResponse<List<ReadPurchaseCouponResponse>> readPurchaseCoupon(@PathVariable Long purchaseId);

	@PostMapping("/bookstore/purchases/{purchaseId}/coupons/{couponId}")
	ApiResponse<Long> createPurchaseCoupon(@PathVariable Long purchaseId,
		@PathVariable Long couponId,
		@RequestBody Integer discountPrice);

	@GetMapping("/bookstore/members/coupons")
	ApiResponse<List<ReadCouponFormResponse>> readCoupons();
}
