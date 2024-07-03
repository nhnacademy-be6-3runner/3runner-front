package com.nhnacademy.front.purchase.purchase.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchase;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseResponse;

public interface PurchaseDetailGuestService {
	ReadPurchaseResponse readGuestPurchases(String orderNumber, String password);

	Page<ReadPurchaseBookResponse> readGuestPurchaseBooks(String orderNumber,int page,int size,String sort);
	boolean validatePurchase(String userId, String password);

	void updatePurchaseStatus(String purchaseId);
}
