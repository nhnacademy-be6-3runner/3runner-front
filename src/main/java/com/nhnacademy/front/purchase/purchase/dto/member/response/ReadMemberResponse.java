package com.nhnacademy.front.purchase.purchase.dto.member.response;

import lombok.Builder;

@Builder
public record ReadMemberResponse(
        Long memberId,
        String name,
        int age,
        String phone,
        String email) {
}
