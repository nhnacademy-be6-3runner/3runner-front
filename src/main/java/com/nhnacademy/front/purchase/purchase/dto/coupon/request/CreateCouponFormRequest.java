package com.nhnacademy.front.purchase.purchase.dto.coupon.request;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record CreateCouponFormRequest(
        ZonedDateTime startDate,
        ZonedDateTime endDate,
        String name,
        Integer maxPrice,
        Integer minPrice,
        Long couponTypeId,
        Long couponUsageId) {
}
