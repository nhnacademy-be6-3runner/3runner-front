package com.nhnacademy.front.token.dto.request;

import lombok.Builder;

@Builder
public record RefreshRequest(
	String refreshToken
) {
}
