package com.nhnacademy.bookstore.member.memberAuth.dto.response;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record MemberAuthResponse(
	@NotNull String email, @NotNull @Size(min = 1, max = 50) String password, @NotNull List<String> auth,
	@NotNull Long memberId) {
}