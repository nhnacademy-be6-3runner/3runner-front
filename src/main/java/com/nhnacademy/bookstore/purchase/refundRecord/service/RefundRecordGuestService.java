package com.nhnacademy.bookstore.purchase.refundRecord.service;

import java.util.List;

import com.nhnacademy.bookstore.entity.refund.Refund;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadBookByPurchase;
import com.nhnacademy.bookstore.purchase.refundRecord.dto.response.ReadRefundRecordResponse;

public interface RefundRecordGuestService {
	Boolean createAllRefundRecordRedis(String orderNumber);

	Long createRefundRecordRedis(String orderNumber, Long purchaseBookId, int price, int quantity,
		ReadBookByPurchase readBookByPurchase);

	Long updateRefundRecordRedis(String orderNumber, Long purchaseBookId, int quantity);

	Long deleteRefundRecordRedis(String orderNumber, Long purchaseBookId);

	List<ReadRefundRecordResponse> readRefundRecordRedis(String orderNumber);

	Boolean createRefundRecord(String orderNumber, Long refundId);

	Boolean updateRefundRecordAllRedis(String orderNumber);

	Boolean updateRefundRecordZeroAllRedis(String orderNumber);
}
