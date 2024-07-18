package com.nhnacademy.front.refund.service.impl;

import org.springframework.stereotype.Service;

import com.nhnacademy.front.refund.feign.RefundRecordGuestControllerClient;
import com.nhnacademy.front.refund.service.RefundRecordGuestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefundRecordGuestServiceImpl implements RefundRecordGuestService {
	private final RefundRecordGuestControllerClient refundRecordGuestControllerClient;

	@Override
	public void updateRefundRecorderGuest(String orderNumber,Long purchaseBookId, int quantity){
		refundRecordGuestControllerClient.updateRefundRecordGuest(orderNumber,purchaseBookId, quantity);
	}

	@Override
	public void updateRefundGuestAll(String orderNumber){
		refundRecordGuestControllerClient.updateRefundRecordAllGuest(orderNumber);
	}

	@Override
	public void updateRefundRecordAllZero(String orderNumber){
		refundRecordGuestControllerClient.updateRefundRecordAllZeroGuest(orderNumber);
	}


}
