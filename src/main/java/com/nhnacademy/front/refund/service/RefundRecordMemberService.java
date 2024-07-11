package com.nhnacademy.front.refund.service;

public interface RefundRecordMemberService {
	void updateRefundRecorderMember(Long purchaseBookId, int quantity);

	void updateRefundAll(Long orderNumber);

	void createRefundRecordMember(Long refundId);
}
