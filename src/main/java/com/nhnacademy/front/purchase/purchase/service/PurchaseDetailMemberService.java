package com.nhnacademy.front.purchase.purchase.service;

import org.springframework.data.domain.Page;

import com.nhnacademy.front.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchase;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseBookResponse;

public interface PurchaseDetailMemberService {

	Page<ReadPurchase> readPurchases(Long userId);

	Page<ReadPurchaseBookResponse> readPurchaseBookResponses(Long purchaseId, int page, int size, String sort);

	PurchaseStatus readPurchaseStatus(Long purchaseId);

	void updatePurchaseStatus(long purchaseId);
}
