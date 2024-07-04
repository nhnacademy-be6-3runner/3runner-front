package com.nhnacademy.front.purchase.purchase.dto.coupon.request;

import lombok.Builder;

@Builder
public record CreateFixedCouponRequest(int discountPrice) {
}
