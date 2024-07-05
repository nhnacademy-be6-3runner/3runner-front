package com.nhnacademy.front.purchase.purchase.dto.coupon;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record CouponDiscountPriceDto(
        Long couponFormId,
        ZonedDateTime startDate,
        ZonedDateTime endDate,
        ZonedDateTime createdAt,
        String name,
        UUID code,
        Integer maxPrice,
        Integer minPrice,
        Long couponTypeId,
        Long couponUsageId,
        String type,
        String usage,
        List<Long> books,
        List<Long> categorys,
        Integer discountPrice,
        Double discountRate,
        Integer discountMax,
        Integer finalDiscountPrice) {
}
