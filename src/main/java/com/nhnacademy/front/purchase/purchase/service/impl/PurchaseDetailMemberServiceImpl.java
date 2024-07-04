package com.nhnacademy.front.purchase.purchase.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.front.purchase.purchase.dto.purchase.request.UpdatePurchaseMemberRequest;
import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchase;
import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseResponse;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseBookControllerClient;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseMemberControllerClient;
import com.nhnacademy.front.purchase.purchase.service.PurchaseDetailMemberService;
import com.nhnacademy.util.ApiResponse;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class PurchaseDetailMemberServiceImpl implements PurchaseDetailMemberService {

	private final PurchaseBookControllerClient purchaseBookControllerClient;
	private final PurchaseMemberControllerClient purchaseMemberControllerClient;



	@Override
	public Page<ReadPurchase> readPurchases(Long memberId){
		List<ReadPurchase> orderDetail = new ArrayList<>();
		ApiResponse<Page<ReadPurchaseResponse>> responses =  purchaseMemberControllerClient.readPurchases(memberId,0,3,null);
		for(ReadPurchaseResponse response : responses.getBody().getData()){
			orderDetail.add(ReadPurchase.builder()
					.id(response.id())
					.orderNumber(response.orderNumber().toString())
					.memberType(response.memberType())
					.road(response.road())
					.createdAt(
						response.createdAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"))+" "+response.createdAt().getZone())
					.deliveryPrice(response.deliveryPrice())
					.password(response.password())
					.status(response.status())
					.totalPrice(response.totalPrice())
				.build());

		}
		return new PageImpl<>(orderDetail);
	}

	@Override
	public Page<ReadPurchaseBookResponse> readPurchaseBookResponses(Long purchaseId, int page, int size, String sort){
		return purchaseBookControllerClient.readPurchaseBook(purchaseId, page,size,null).getBody().getData();
	}

	@Override
	public PurchaseStatus readPurchaseStatus(Long purchaseId){
		return purchaseMemberControllerClient.readPurchase(1L,purchaseId).getBody().getData().status();
	}

	@Override
	public void updatePurchaseStatus(long purchaseId){
		purchaseMemberControllerClient.updatePurchaseStatus(1L, UpdatePurchaseMemberRequest.builder().purchaseStatus(PurchaseStatus.CONFIRMED).build(),purchaseId);
	}

}
