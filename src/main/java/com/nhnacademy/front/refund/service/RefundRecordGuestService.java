package com.nhnacademy.front.refund.service;

/**
 *  비회원 환불 서비스
 *
 * @author 정주혁
 */
public interface RefundRecordGuestService {

	/**
	 * 임시저장 환불 내역 수정
	 *
	 * @param orderNumber 주문 orderNumber
	 * @param purchaseBookId 주문 책 -id
	 * @param quantity 수량
	 */
	void updateRefundRecorderGuest(String orderNumber,Long purchaseBookId, int quantity);

	/**
	 * 임시저장 환불 내역 모두 수정(최댓값)
	 *
	 * @param orderNumber 주문 orderNumber
	 */
	void updateRefundGuestAll(String orderNumber);

	/**
	 * 임시저장 환불내역 모두 수정(0)
	 *
	 * @param orderNumber
	 */
	void updateRefundRecordAllZero(String orderNumber);

}
