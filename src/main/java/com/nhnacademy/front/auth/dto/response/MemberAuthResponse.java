package com.nhnacademy.front.auth.dto.response;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * 멤버 정보를 받아올
 * @param email 이메일
 * @param auth 권한
 * @param memberId 멤버 아이디
 */
@Builder
public record MemberAuthResponse(
	@NotNull String email,
	@NotNull List<String> auth,
	@NotNull Long memberId
) {
}
