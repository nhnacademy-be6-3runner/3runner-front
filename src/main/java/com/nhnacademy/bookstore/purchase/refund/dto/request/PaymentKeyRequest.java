package com.nhnacademy.bookstore.purchase.refund.dto.request;

import lombok.Builder;

@Builder
public record PaymentKeyRequest(String paymentKey) {
}
