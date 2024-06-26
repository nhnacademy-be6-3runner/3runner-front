package com.nhnacademy.front.member.address.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * Front server 로부터 받는 Login Request DTO
 */
@Builder
public record LoginRequest(@Email @NotNull String email, @NotNull String password) {
}
