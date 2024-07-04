package com.nhnacademy.front.purchase.purchase.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.front.purchase.purchase.dto.request.UpdatePurchaseMemberRequest;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchase;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseBookControllerClient;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseMemberControllerClient;
import com.nhnacademy.front.purchase.purchase.service.PurchaseDetailMemberService;
import com.nhnacademy.util.ApiResponse;

import lombok.RequiredArgsConstructor;

/**
 * 회원 주문 내역 service
 *
 * @author 정주혁
 */
@RequiredArgsConstructor
@Service
public class PurchaseDetailMemberServiceImpl implements PurchaseDetailMemberService {

	private final PurchaseBookControllerClient purchaseBookControllerClient;
	private final PurchaseMemberControllerClient purchaseMemberControllerClient;

	/**
	 * 주문 내역 조회
	 *
	 * @return 주문 내역 Page 반환
	 */
	@Override
	public Page<ReadPurchase> readPurchases(){
		List<ReadPurchase> orderDetail = new ArrayList<>();
		ApiResponse<Page<ReadPurchaseResponse>> responses =  purchaseMemberControllerClient.readPurchases( 0,3,null);
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

	/**
	 * 회원 주문 내역 조회
	 *
	 * @param purchaseId 주문 아이디
	 * @param page 현재 페이지
	 * @param size 보여줄 개수
	 * @param sort 정렬
	 * @return 해당 주문 - 책 page 리스트
	 */
	@Override
	public Page<ReadPurchaseBookResponse> readPurchaseBookResponses(Long purchaseId, int page, int size, String sort){
		return purchaseBookControllerClient.readPurchaseBook(purchaseId, page,size,null).getBody().getData();
	}

	/**
	 * 회원 주문 상태 조회
	 *
	 * @param purchaseId 상태 조회할 주문id
	 * @return 해당 주문 상태
	 */
	@Override
	public PurchaseStatus readPurchaseStatus(Long purchaseId){
		return purchaseMemberControllerClient.readPurchase(purchaseId).getBody().getData().status();
	}

	/**
	 * 회원 주문 상태 -> 주문 확정 변경
	 *
	 * @param purchaseId 확정으로 변경할 주문 id
	 */
	@Override
	public void updatePurchaseStatus(long purchaseId){
		purchaseMemberControllerClient.updatePurchaseStatus( UpdatePurchaseMemberRequest.builder().purchaseStatus(PurchaseStatus.CONFIRMED).build(),purchaseId);
	}

}
