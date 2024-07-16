package com.nhnacademy.front.refund.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.refund.dto.response.ReadRefundResponse;

/**
 * 환불 종합 서비스
 *
 * @author 정주혁
 *
 */
public interface RefundService {

	/**
	 * 주문에 해당하는 주문책들 가져오기
	 * @param purchaseId
	 * @return
	 */
	List<ReadPurchaseBookResponse> readMemberPurchaseBooks(Long purchaseId);

	/**
	 * toss api 사용
	 * @param orderNumber
	 * @param price
	 * @param cancelReason
	 * @return
	 */
	Map<String, Object> refundToss(Object orderNumber,Integer price,String cancelReason);

	/**
	 * 환불 생성
	 * @param orderId
	 * @param price
	 * @param refundReason
	 * @return
	 */
	Long createRefundRequest(Long orderId, Integer price, String refundReason);

	/**
	 * 주문 orderNumber를 통한 주문 - 책 모두 조회
	 * @param orderNumber
	 * @return
	 */
	List<ReadPurchaseBookResponse> readGuestPurchaseBooks(String orderNumber);

	/**
	 * 환불 수락
	 *
	 * @param refundId
	 * @return
	 */
	Boolean updateRefundSuccess(Long refundId);

	/**
	 * 환불 거절
	 * @param refundId
	 * @return
	 */
	Boolean updateRefundReject(Long refundId);

	/**
	 * 모든 환불 조회
	 * @return
	 */
	List<ReadRefundResponse> readRefundAll();
}
