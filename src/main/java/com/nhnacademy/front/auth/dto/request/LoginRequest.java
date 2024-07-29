package com.nhnacademy.front.auth.dto.request;

import lombok.Builder;

/**
 * Auth 서버로 로그인 요청을 보내는 DTO.
 *
 * @author 오연수
 */
@Builder
public record LoginRequest(String email, String password) {
}
