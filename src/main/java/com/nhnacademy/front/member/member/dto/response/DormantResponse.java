package com.nhnacademy.front.member.member.dto.response;

import lombok.Builder;

@Builder
public record DormantResponse(String access, String refresh) {
}
