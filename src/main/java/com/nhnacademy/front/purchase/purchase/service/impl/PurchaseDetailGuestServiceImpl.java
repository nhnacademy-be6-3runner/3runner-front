package com.nhnacademy.front.purchase.purchase.service.impl;

import java.util.Objects;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.purchase.purchase.dto.request.ReadDeletePurchaseGuestRequest;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchase;
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
	public Page<ReadPurchase> readGuestPurchases(Long memberId){
		// ApiResponse<ReadPurchaseResponse> readPurchase =  purchaseGuestControllerClient.readPurchase();
		return null;
	}
	@Override
	public boolean validatePurchase(String userId, String password) {
		return !Objects.isNull(
			purchaseGuestControllerClient.readPurchase(ReadDeletePurchaseGuestRequest.builder().orderNumber(
				UUID.fromString(userId)).password(password).build()).getBody().getData());
	}

}
