package com.nhnacademy.front.purchase.purchase.service;

import org.springframework.data.domain.Page;

import com.nhnacademy.front.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchase;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseBookResponse;


/**
 * 회원 주문조회 service Interface
 *
 * @author 정주혁
 */
public interface PurchaseDetailMemberService {

	Page<ReadPurchase> readPurchases();

	Page<ReadPurchaseBookResponse> readPurchaseBookResponses(Long purchaseId, int page, int size, String sort);

	PurchaseStatus readPurchaseStatus(Long purchaseId);

	void updatePurchaseStatus(long purchaseId);
}
