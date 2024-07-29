package com.nhnacademy.front.purchase.purchase.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseResponse;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseManagerControllerClient;
import com.nhnacademy.front.purchase.purchase.service.PurchaseDetailManagerService;

import lombok.RequiredArgsConstructor;

/**
 * 관리자 주문 내역 조회 service.
 *
 * @author 정주혁
 */
@Service
@RequiredArgsConstructor
public class PurchaseDetailManagerServiceImpl implements PurchaseDetailManagerService {
	private final PurchaseManagerControllerClient purchaseManagerControllerClient;

	/**
	 * 모든 주문내역조회.
	 *
	 * @param page 현재 페이지
	 * @param size 현재 페이지에서 보여줄 개수
	 * @param sort 정렬
	 * @return 주문내역 page
	 */
	@Override
	public Page<ReadPurchaseResponse> readPurchase(int page, int size, String sort) {
		return purchaseManagerControllerClient.readPurchases(page, size, sort).getBody().getData();
	}

	/**
	 * 주문 상태 변경.
	 *
	 * @param purchase 상태 변경할 주문OrderNumber
	 * @param status 상태
	 * @return 변경된 주문 id
	 */
	@Override
	public Long updatePurchaseStatus(String purchase, String status) {
		return purchaseManagerControllerClient.purchaseUpdate(purchase, status).getBody().getData();
	}

}
