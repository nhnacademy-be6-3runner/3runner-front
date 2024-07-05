package com.nhnacademy.front.purchase.purchase.dto.coupon.response;

import lombok.Builder;

@Builder
public record ReadCouponTypeResponse(Long couponTypeId, String type) {
}
