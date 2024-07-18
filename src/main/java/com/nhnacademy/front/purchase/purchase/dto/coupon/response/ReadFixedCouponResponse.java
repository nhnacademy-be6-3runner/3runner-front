package com.nhnacademy.front.purchase.purchase.dto.coupon.response;

import lombok.Builder;

@Builder
public record ReadFixedCouponResponse(
        Long fixedCouponId,
        Long couponTypeId,
        int discountPrice) {
}
