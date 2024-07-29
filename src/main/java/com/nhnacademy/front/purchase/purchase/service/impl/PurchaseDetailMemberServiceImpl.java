package com.nhnacademy.front.purchase.purchase.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.nhnacademy.front.util.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.front.purchase.purchase.dto.purchase.request.UpdatePurchaseMemberRequest;
import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchase;
import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseResponse;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseBookControllerClient;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseMemberControllerClient;
import com.nhnacademy.front.purchase.purchase.service.PurchaseDetailMemberService;
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
	 * 주문 내역 조회.
	 *
	 * @return 주문 내역 Page 반환
	 */
	@Override
	public Page<ReadPurchase> readPurchases(int page) {
		List<ReadPurchase> orderDetail = new ArrayList<>();
		ApiResponse<List<ReadPurchaseResponse>> responses = purchaseMemberControllerClient.readPurchases();
		for (ReadPurchaseResponse response : responses.getBody().getData()) {
			orderDetail.add(ReadPurchase.builder()
				.id(response.id())
				.orderNumber(response.orderNumber().toString())
				.memberType(response.memberType())
				.road(response.road())
				.createdAt(
					response.createdAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH")) + " "
						+ response.createdAt().getZone())
				.deliveryPrice(response.deliveryPrice())
				.password(response.password())
				.status(response.status())
				.totalPrice(response.totalPrice())
				.build());

		}
		Pageable pageable = PageRequest.of(page, 5, Sort.by("createdAt").descending());
		int start = (int)pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), orderDetail.size());
		List<ReadPurchase> pagedResponse = orderDetail.subList(start, end);

		return new PageImpl<>(pagedResponse, pageable, orderDetail.size());
	}

	/**
	 * 회원 주문 내역 조회.
	 *
	 * @param purchaseId 주문 아이디
	 * @return 해당 주문 - 책 page 리스트
	 */
	@Override
	public List<ReadPurchaseBookResponse> readPurchaseBookResponses(Long purchaseId) {
		return purchaseBookControllerClient.readPurchaseBook(purchaseId).getBody().getData();
	}

	/**
	 * 회원 주문 상태 조회.
	 *
	 * @param purchaseId 상태 조회할 주문id
	 * @return 해당 주문 상태
	 */
	@Override
	public PurchaseStatus readPurchaseStatus(Long purchaseId) {
		return purchaseMemberControllerClient.readPurchase(purchaseId).getBody().getData().status();
	}

	/**
	 * 회원 주문 상태 -> 주문 확정 변경.
	 *
	 * @param purchaseId 확정으로 변경할 주문 id
	 */
	@Override
	public void updatePurchaseStatus(long purchaseId) {
		purchaseMemberControllerClient.updatePurchaseStatus(
			UpdatePurchaseMemberRequest.builder().purchaseStatus(PurchaseStatus.CONFIRMATION).build(), purchaseId);
	}

}
