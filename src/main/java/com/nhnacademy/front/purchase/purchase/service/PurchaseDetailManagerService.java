package com.nhnacademy.front.purchase.purchase.service;

import org.springframework.data.domain.Page;

import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseResponse;

public interface PurchaseDetailManagerService {
	Page<ReadPurchaseResponse> readPurchase(int size,int page, String sort);
}
