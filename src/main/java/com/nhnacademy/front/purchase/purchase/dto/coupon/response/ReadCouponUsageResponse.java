package com.nhnacademy.front.purchase.purchase.dto.coupon.response;

import lombok.Builder;

@Builder
public record ReadCouponUsageResponse(Long couponUsageId, String usage) {
}
