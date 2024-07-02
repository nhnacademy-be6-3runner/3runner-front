package com.nhnacademy.bookstore.purchase.purchase.service;

import java.util.List;

import com.nhnacademy.bookstore.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.bookstore.purchase.purchase.dto.response.ReadPurchaseResponse;

public interface PurchaseManagerService {

	List<ReadPurchaseResponse> readPurchaseAll();

	Long updatePurchaseStatus(long memberId, String purchaseId, String status);
}
