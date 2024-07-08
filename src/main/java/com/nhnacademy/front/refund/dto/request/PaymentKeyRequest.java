package com.nhnacademy.front.refund.dto.request;

import lombok.Builder;

@Builder
public record PaymentKeyRequest(String paymentKey) {
}
