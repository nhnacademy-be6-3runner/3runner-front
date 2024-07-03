package com.nhnacademy.bookstore.purchase.coupon.service;

import com.nhnacademy.bookstore.purchase.coupon.feign.dto.request.CreateCouponFormRequest;
import com.nhnacademy.bookstore.purchase.coupon.feign.dto.response.ReadCouponTypeResponse;
import com.nhnacademy.bookstore.purchase.coupon.feign.dto.response.ReadCouponUsageResponse;

import java.util.List;

public interface CouponAdminService {
    List<ReadCouponTypeResponse> readTypes();

    List<ReadCouponUsageResponse> readUsages();

    Long createCoupon(CreateCouponFormRequest createCouponFormRequest, Long memberId);
}
