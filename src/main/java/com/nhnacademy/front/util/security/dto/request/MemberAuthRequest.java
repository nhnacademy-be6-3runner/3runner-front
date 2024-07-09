package com.nhnacademy.front.util.security.dto.request;

import lombok.Builder;

@Builder
public record MemberAuthRequest(String email) {
}
