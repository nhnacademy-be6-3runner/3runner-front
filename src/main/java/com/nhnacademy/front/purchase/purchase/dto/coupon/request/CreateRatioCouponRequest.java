package com.nhnacademy.front.purchase.purchase.dto.coupon.request;

import lombok.Builder;

@Builder
public record CreateRatioCouponRequest(double discountRate, int discountMaxPrice) {
}
