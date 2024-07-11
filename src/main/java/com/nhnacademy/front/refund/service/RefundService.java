package com.nhnacademy.front.refund.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.refund.dto.response.ReadRefundResponse;

public interface RefundService {

	List<ReadPurchaseBookResponse> readMemberPurchaseBooks(Long purchaseId);

	Map<String, Object> refundToss(String orderNumber,Integer price,String cancelReason);

	Long createRefundRequest(Long orderId, Integer price, String refundReason);

	List<ReadPurchaseBookResponse> readGuestPurchaseBooks(String orderNumber);

	ReadRefundResponse readRefund(Long refundId);

	Boolean updateRefundSuccess(Long refundId);

	Boolean updateRefundReject(Long refundId);

	List<ReadRefundResponse> readRefundAll();
}
