package com.nhnacademy.bookstore.purchase.refund.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Locked;

@Builder
public record ReadRefundResponse(String refundContent, Integer price, Long refundId, String orderNumber) {
	public ReadRefundResponse(String refundContent, Integer price, Long refundId, UUID orderNumber) {
		this(refundContent, price, refundId, orderNumber.toString());
	}

}
