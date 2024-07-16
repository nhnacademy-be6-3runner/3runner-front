package com.nhnacademy.front.refund.service;

public interface RefundRecordMemberService {
	void updateRefundRecorderMember(Long orderNumber,Long purchaseBookId, int quantity);

	void updateRefundAll(Long orderNumber);

	void createRefundRecordMember(Long orderNumber, Long refundId);
}
