package com.nhnacademy.front.purchase.purchase.dto.coupon.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record CreateRatioCouponRequest(
        @Min(0)@Max(100) double discountRate,
        @Min(0) int discountMaxPrice) {
}
