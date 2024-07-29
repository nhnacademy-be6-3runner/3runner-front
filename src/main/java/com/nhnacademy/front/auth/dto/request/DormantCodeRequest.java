package com.nhnacademy.front.auth.dto.request;

import lombok.Builder;

@Builder
public record DormantCodeRequest(String code) {
}
