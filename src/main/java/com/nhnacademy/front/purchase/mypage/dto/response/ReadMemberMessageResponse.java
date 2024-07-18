package com.nhnacademy.front.purchase.mypage.dto.response;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record ReadMemberMessageResponse(
        long id,
        String message,
        ZonedDateTime sendAt,
        ZonedDateTime viewAt) {
    }
