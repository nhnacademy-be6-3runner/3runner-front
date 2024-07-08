package com.nhnacademy.front.refund.dto.response;

import lombok.Builder;

@Builder
public record ReadRefundResponse(String refundContent, Integer price, Long refundId, String orderNumber) {
}
