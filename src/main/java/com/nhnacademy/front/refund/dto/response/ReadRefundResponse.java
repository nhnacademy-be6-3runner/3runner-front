package com.nhnacademy.front.refund.dto.response;

import java.util.UUID;

import lombok.Builder;

@Builder
public record ReadRefundResponse(String refundContent, Integer price, Long refundId, RefundStatus status, String orderNumber) {
	public ReadRefundResponse(String refundContent, Integer price, Long refundId, RefundStatus status, UUID orderNumber) {
		this(refundContent, price, refundId, status, orderNumber.toString());
	}

}
