package com.nhnacademy.front.purchase.purchase.dto.coupon.request;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateCategoryCouponRequest(List<Long> categoryIds) {
}
