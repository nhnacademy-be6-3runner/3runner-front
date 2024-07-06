package com.nhnacademy.front.purchase.admin.feign;

import com.nhnacademy.front.book.categroy.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateCouponFormRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.ReadCouponFormRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponFormResponse;
import com.nhnacademy.front.util.ApiResponse;
import com.nhnacademy.global.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "CouponFormControllerClient", url = "http://${feign.client.url}" , configuration = FeignConfiguration.class)
public interface CouponFormControllerClient {

    @GetMapping("/coupon/forms")
    ApiResponse<List<ReadCouponFormResponse>> readAllCouponForms();

    @PostMapping("/coupon/forms")
    ApiResponse<Long> createCouponForm(@RequestBody CreateCouponFormRequest createCouponFormRequest);
}
