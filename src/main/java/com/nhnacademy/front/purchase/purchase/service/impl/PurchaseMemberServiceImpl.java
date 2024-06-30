package com.nhnacademy.front.purchase.purchase.service.impl;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchase;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseBookControllerClient;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseMemberControllerClient;
import com.nhnacademy.front.purchase.purchase.service.PurchaseMemberService;
import com.nhnacademy.util.ApiResponse;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class PurchaseMemberServiceImpl implements PurchaseMemberService {

	private final PurchaseBookControllerClient purchaseBookControllerClient;
	private final PurchaseMemberControllerClient purchaseMemberControllerClient;



	@Override
	public Page<ReadPurchase> readPurchases(Long memberId){
		List<ReadPurchase> orderDetail = new ArrayList<>();
		ApiResponse<Page<ReadPurchaseResponse>> responses =  purchaseMemberControllerClient.readPurchases(memberId,0,3,null);
		for(ReadPurchaseResponse response : responses.getBody().getData()){
			Page<ReadPurchaseBookResponse> bookByPurchases = purchaseBookControllerClient.readPurchaseBook(response.id(), 0,5,null).getBody().getData();
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
					.readPurchaseBookResponses(bookByPurchases)
				.build());

		}
		return new PageImpl<>(orderDetail);
	}

}
