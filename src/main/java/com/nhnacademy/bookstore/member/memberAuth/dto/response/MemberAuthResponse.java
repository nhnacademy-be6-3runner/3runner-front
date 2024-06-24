package com.nhnacademy.bookstore.member.memberAuth.dto.response;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * Auth 서버로 보내는 권한이 담긴 멤버 정보 DTO
 *
 * @author 오연수
 */
@Builder
public record MemberAuthResponse(
	@NotNull String email, @NotNull @Size(min = 1, max = 50) String password, @NotNull List<String> auth,
	@NotNull Long memberId) {
}