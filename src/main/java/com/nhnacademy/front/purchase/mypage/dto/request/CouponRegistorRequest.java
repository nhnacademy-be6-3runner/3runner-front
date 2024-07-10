package com.nhnacademy.front.purchase.mypage.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CouponRegistorRequest(
        @NotBlank String code) {
    }
