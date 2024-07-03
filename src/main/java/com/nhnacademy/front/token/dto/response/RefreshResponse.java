package com.nhnacademy.front.token.dto.response;

import lombok.Builder;

@Builder
public record RefreshResponse(
	String message,
	String accessToken
) {
}
