package com.nhnacademy.bookstore.purchase.refund.service;

import com.nhnacademy.bookstore.purchase.refund.dto.response.ReadRefundResponse;

public interface RefundService {
	String readTossOrderId(String orderId);

	Long createRefund(Long orderId, String refundContent, Integer price, Long memberId);

	Boolean updateSuccessRefund(Long refundId);

	Boolean updateRefundRejected(Long refundId);

	ReadRefundResponse readRefund(Long refundRecordId);

	Long createRefundCancelPayment(String orderId);


}
