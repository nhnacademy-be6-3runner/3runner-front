package com.nhnacademy.front.purchase.mypage.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UpdateMemberMessageRequest(
        @NotNull@Min(0) Long memberMessageId) {
    }
