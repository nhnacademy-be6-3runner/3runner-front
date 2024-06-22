package com.nhnacademy.front.auth.dto.request;

import lombok.Builder;

@Builder
public record LoginRequest(String email, String password) {
}
