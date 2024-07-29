package com.nhnacademy.front.purchase.admin.feign;

import com.nhnacademy.front.purchase.purchase.dto.coupon.request.*;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.*;
import com.nhnacademy.front.util.ApiResponse;
import com.nhnacademy.global.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "CouponPolicyControllerClient", url = "${feign.client.url}", configuration = FeignConfiguration.class)
public interface CouponPolicyControllerClient {
	@PostMapping("/coupon/types/fixes")
	ApiResponse<Long> createFixedCouponPolicy(@RequestBody CreateFixedCouponRequest createFixedCouponRequest);

	@PostMapping("/coupon/types/ratios")
	ApiResponse<Long> createRatioCouponPolicy(@RequestBody CreateRatioCouponRequest createRatioCouponRequest);

	@PostMapping("/coupon/usages/categories")
	ApiResponse<Long> createCategoryCouponPolicy(@RequestBody CreateCategoryCouponRequest createCategoryCouponRequest);

	@PostMapping("/coupon/usages/books")
	ApiResponse<Long> createBookCouponPolicy(@RequestBody CreateBookCouponRequest createBookCouponRequest);

	@GetMapping("/coupon/types")
	ApiResponse<List<ReadCouponTypeResponse>> readAllTypes();

	@GetMapping("/coupon/types/fixes/{couponTypeId}")
	ApiResponse<ReadFixedCouponResponse> readFixedType(@PathVariable Long couponTypeId);

	@GetMapping("/coupon/types/ratios/{couponTypeId}")
	ApiResponse<ReadRatioCouponResponse> readRatioType(@PathVariable Long couponTypeId);

	@GetMapping("/coupon/usages")
	ApiResponse<List<ReadCouponUsageResponse>> readAllUsages();

	@GetMapping("/coupon/usages/categories/{couponTypeId}")
	ApiResponse<List<Long>> readCategoryUsages(@PathVariable Long couponTypeId);

	@GetMapping("/coupon/usages/books/{couponTypeId}")
	ApiResponse<List<Long>> readBookUsages(@PathVariable Long couponTypeId);
}
