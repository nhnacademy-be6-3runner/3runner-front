package com.nhnacademy.bookstore.purchase.refund.service;

import com.nhnacademy.bookstore.purchase.refund.dto.response.ReadRefundResponse;

public interface RefundService {
	String readTossOrderId(String orderId);

	Long createRefund(Long orderId, String refundContent, Integer price);

	Boolean updateSuccessRefund(Long refundRecordId);

	Boolean updateRefundRejected(Long refundRecordId);

	ReadRefundResponse readRefund(Long refundRecordId);

	Boolean createRefundCancelPayment(String orderId);


}
