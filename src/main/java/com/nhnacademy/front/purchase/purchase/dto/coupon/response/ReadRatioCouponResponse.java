package com.nhnacademy.front.purchase.purchase.dto.coupon.response;

import lombok.Builder;

@Builder
public record ReadRatioCouponResponse(
        Long ratioCouponId,
        Long couponTypeId,
        double discountRate,
        int discountMaxPrice) {
}
