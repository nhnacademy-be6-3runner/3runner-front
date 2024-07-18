package com.nhnacademy.front.purchase.purchase.service.impl;

import java.util.List;
import java.util.UUID;

import com.nhnacademy.front.util.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.front.purchase.purchase.dto.purchase.request.UpdatePurchaseGuestRequest;
import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseResponse;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseBookControllerClient;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseGuestControllerClient;
import com.nhnacademy.front.purchase.purchase.service.PurchaseDetailGuestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseDetailGuestServiceImpl implements PurchaseDetailGuestService {
	private final PurchaseGuestControllerClient purchaseGuestControllerClient;
	private final PurchaseBookControllerClient purchaseBookControllerClient;

	@Override
	public ReadPurchaseResponse readGuestPurchases(String orderNumber, String password){
		ApiResponse<ReadPurchaseResponse> readPurchase =  purchaseGuestControllerClient.readPurchase(orderNumber, password);
		return readPurchase.getBody().getData();
	}

	@Override
	public List<ReadPurchaseBookResponse> readGuestPurchaseBooks(String orderNumber) {

		return purchaseBookControllerClient.readGuestPurchaseBook(orderNumber).getBody().getData();
	}

	@Override
	public boolean validatePurchase(String orderNumber, String password) {
		return purchaseGuestControllerClient.validatePurchases(orderNumber, password).getBody().getData();
	}

	@Override
	public void updatePurchaseStatus(String purchaseId){
		purchaseGuestControllerClient.updatePurchaseStatus( UpdatePurchaseGuestRequest.builder().purchaseStatus(
			PurchaseStatus.CONFIRMATION).orderNumber(UUID.fromString(purchaseId)).build());
	}
}
