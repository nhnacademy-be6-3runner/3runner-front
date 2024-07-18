package com.nhnacademy.front.purchase.purchase.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseResponse;


/**
 * 비회원 주문조회 service Interface
 *
 * @author 정주혁
 */
public interface PurchaseDetailGuestService {
	ReadPurchaseResponse readGuestPurchases(String orderNumber, String password);

	List<ReadPurchaseBookResponse> readGuestPurchaseBooks(String orderNumber);
	boolean validatePurchase(String userId, String password);

	void updatePurchaseStatus(String purchaseId);
}
