package com.nhnacademy.front.refund.service.impl;

import org.springframework.stereotype.Service;

import com.nhnacademy.front.refund.feign.RefundRecordGuestControllerClient;
import com.nhnacademy.front.refund.service.RefundRecordGuestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefundRecordGuestServiceImpl implements RefundRecordGuestService {
	private final RefundRecordGuestControllerClient refundRecordGuestControllerClient;

	public void updateRefundRecorderGuest(String orderNumber,Long purchaseBookId, int quantity){
		refundRecordGuestControllerClient.updateRefundRecordGuest(orderNumber,purchaseBookId, quantity);
	}

	public void updateRefundGuestAll(String orderNumber){
		refundRecordGuestControllerClient.updateRefundRecordAllGuest(orderNumber);
	}

	public void createRefundRecordGuest(String orderNumber, Long refundId){
		refundRecordGuestControllerClient.createRefundRecordGuest(orderNumber, refundId);
	}
}
