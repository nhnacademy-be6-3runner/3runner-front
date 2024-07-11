package com.nhnacademy.bookstore.purchase.refundRecord.service;

import java.util.List;

import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadBookByPurchase;
import com.nhnacademy.bookstore.purchase.refundRecord.dto.response.ReadRefundRecordResponse;

public interface RefundRecordMemberService {
	Long createRefundRecordRedis(Long memberId, Long purchaseBookId, int price, int quantity,
		ReadBookByPurchase readBookByPurchase);

	Long createRefundRecordAllRedis(Long memberId, Long orderNumber);

	Long updateRefundRecordRedis(Long memberId, Long purchaseBookId, int quantity);

	Long updateRefundRecordAllRedis(Long memberId, Long orderNumber);

	Long updateRefundRecordZeroAllRedis(Long memberId, Long orderNumber);

	Long deleteRefundRecordRedis(Long memberId, Long purchaseBookId);

	List<ReadRefundRecordResponse> readRefundRecordRedis(Long memberId);

	Boolean createRefundRecord(Long memberId, Long refundId);


}
