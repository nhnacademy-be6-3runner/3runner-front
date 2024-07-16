package com.nhnacademy.front.refund.service.impl;

import org.springframework.stereotype.Service;

import com.nhnacademy.front.refund.feign.RefundRecordMemberControllerClient;
import com.nhnacademy.front.refund.service.RefundRecordMemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefundRecordMemberServiceImpl implements RefundRecordMemberService {
	private final RefundRecordMemberControllerClient refundRecordMemberControllerClient;

	public void updateRefundRecorderMember(Long orderNumber, Long purchaseBookId, int quantity){
		refundRecordMemberControllerClient.updateRefundRecordMember(orderNumber, purchaseBookId, quantity);
	}

	public void updateRefundAll(Long orderNumber){
		refundRecordMemberControllerClient.updateRefundRecordAllMember(orderNumber);
	}

	public void createRefundRecordMember(Long orderNumber, Long refundId){
		refundRecordMemberControllerClient.createRefundRecordMember(orderNumber, refundId);
	}


}
