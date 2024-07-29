package com.nhnacademy.front.purchase.admin.feign;

import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateCouponFormRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponFormResponse;
import com.nhnacademy.front.util.ApiResponse;
import com.nhnacademy.global.config.FeignConfiguration;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "CouponFormControllerClient", url = "${feign.client.url}", configuration = FeignConfiguration.class)
public interface CouponFormControllerClient {

	@GetMapping("/coupon/forms")
	ApiResponse<List<ReadCouponFormResponse>> readAllCouponForms();

	@PostMapping("/coupon/forms")
	ApiResponse<Long> createCouponForm(@RequestBody CreateCouponFormRequest createCouponFormRequest);

	@PostMapping("/coupon/forms/{quantity}")
	ApiResponse<Void> createCouponFormWithMq(@RequestBody CreateCouponFormRequest createCouponFormRequest,
		@PathVariable Long quantity);
}
