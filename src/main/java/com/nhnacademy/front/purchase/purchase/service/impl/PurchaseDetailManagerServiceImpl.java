package com.nhnacademy.front.purchase.purchase.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseManagerControllerClient;
import com.nhnacademy.front.purchase.purchase.service.PurchaseDetailManagerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseDetailManagerServiceImpl implements PurchaseDetailManagerService {
	private final PurchaseManagerControllerClient purchaseManagerControllerClient;

	@Override
	public Page<ReadPurchaseResponse> readPurchase(int page,int size, String sort) {
		return purchaseManagerControllerClient.readPurchases(page, size, sort).getBody().getData();
	}

}
