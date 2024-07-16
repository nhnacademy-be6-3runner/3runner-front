package com.nhnacademy.front.refund.service;

/**
 * 환불 내역 서비스 (회원)
 *
 * @author 정주혁
 */
public interface RefundRecordMemberService {
	/**
	 * 임시저장 환불 내역 수정
	 *
	 * @param orderNumber 주문 id
	 * @param purchaseBookId 주문 책 id
	 * @param quantity 수량
	 */
	void updateRefundRecorderMember(Long orderNumber,Long purchaseBookId, int quantity);

	/**
	 * 임시 저장 환불 내역 모두 수정(최댓값)
	 *
	 * @param orderNumber 주문 id
	 */
	void updateRefundAll(Long orderNumber);

	/**
	 * 임시 저장 환불 내역 모두 수정(0)
	 *
	 * @param orderNumber 주문 id
	 */
	void updateRefundAllZero(Long orderNumber);

	/**
	 * 임시저장 환불 내역 모두 db로 저장
	 *
	 * @param orderNumber
	 * @param refundId
	 * @return
	 */
	Boolean createRefundRecordMember(Long orderNumber, Long refundId);
}
