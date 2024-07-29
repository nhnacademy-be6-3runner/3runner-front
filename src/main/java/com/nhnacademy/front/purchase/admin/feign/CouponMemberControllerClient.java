package com.nhnacademy.front.purchase.admin.feign;

import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateCouponFormRequest;
import com.nhnacademy.front.purchase.purchase.dto.member.response.ReadMemberResponse;
import com.nhnacademy.front.util.ApiResponse;
import com.nhnacademy.global.config.FeignConfiguration;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "CouponRegisterControllerClient", url = "${feign.client.url}", configuration = FeignConfiguration.class)
public interface CouponMemberControllerClient {
	@GetMapping("/bookstore/admin/members")
	ApiResponse<List<ReadMemberResponse>> getMembers();

	@PostMapping("/bookstore/admin/coupons/{targetMemberId}")
	ApiResponse<Long> createCoupon(@PathVariable Long targetMemberId,
		@RequestBody CreateCouponFormRequest createCouponFormRequest);
}
