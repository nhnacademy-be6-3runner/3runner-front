package com.nhnacademy.front.purchase.purchase.dto.coupon.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record CreateCouponFormRequest(
        @NotBlank ZonedDateTime startDate,
        @NotBlank ZonedDateTime endDate,
        @NotBlank String name,
        @Min(0) Integer maxPrice,
        @Min(0) Integer minPrice,
        @NotBlank Long couponTypeId,
        @NotBlank Long couponUsageId) {
}
