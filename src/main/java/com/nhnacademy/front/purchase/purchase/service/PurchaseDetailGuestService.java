package com.nhnacademy.front.purchase.purchase.service;

import org.springframework.data.domain.Page;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseResponse;

public interface PurchaseDetailGuestService {
	ReadPurchaseResponse readGuestPurchases(String orderNumber, String password);

	Page<ReadPurchaseBookResponse> readGuestPurchaseBooks(String orderNumber,int page,int size,String sort);
	boolean validatePurchase(String userId, String password);

	void updatePurchaseStatus(String purchaseId);
}
