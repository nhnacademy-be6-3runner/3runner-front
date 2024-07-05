package com.nhnacademy.front.refund.service;

import org.springframework.data.domain.Page;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;

public interface RefundService {

	Page<ReadPurchaseBookResponse> readGuestPurchaseBooks(String orderNumber,int page,int size,String sort);
	String readPaymentKey(String orderNumber);
}
