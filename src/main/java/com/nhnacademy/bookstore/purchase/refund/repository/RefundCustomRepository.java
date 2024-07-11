package com.nhnacademy.bookstore.purchase.refund.repository;

import com.nhnacademy.bookstore.purchase.refund.dto.response.ReadRefundResponse;

public interface RefundCustomRepository {
	ReadRefundResponse readRefund(Long refundId);
}
