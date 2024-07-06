package com.nhnacademy.front.purchase.purchase.dto.coupon.request;

import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record CreateFixedCouponRequest(
        @Min(0) int discountPrice) {
}
