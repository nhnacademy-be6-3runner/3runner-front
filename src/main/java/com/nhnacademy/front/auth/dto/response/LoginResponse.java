package com.nhnacademy.front.auth.dto.response;

import lombok.Builder;

/**
 * Auth 서버로부터 로그인 응답받는 DTO.
 *
 * @author 오연수
 */
@Builder
public record LoginResponse(String message) {
}
