package com.nhnacademy.front.purchase.purchase.service;

import org.springframework.data.domain.Page;

import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchase;

public interface PurchaseDetailGuestService {
	Page<ReadPurchase> readGuestPurchases(Long memberId);

	boolean validatePurchase(String userId, String password);
}
