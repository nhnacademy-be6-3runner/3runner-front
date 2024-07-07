package com.nhnacademy.front.refund.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseBookControllerClient;
import com.nhnacademy.front.refund.feign.RefundControllerClient;
import com.nhnacademy.front.refund.service.RefundService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

	private final PurchaseBookControllerClient purchaseBookControllerClient;
	private final RefundControllerClient refundControllerClient;


	@Override
	public Page<ReadPurchaseBookResponse> readGuestPurchaseBooks(String orderNumber,int page,int size,String sort) {
		return purchaseBookControllerClient.readGuestPurchaseBook(orderNumber,page,size,sort).getBody().getData();
	}

	@Override
	public String readPaymentKey(String orderNumber) {
		return refundControllerClient.readTossOrderId(orderNumber).getBody().getData();
	}



}
