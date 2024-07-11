package com.nhnacademy.front.purchase.purchase.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseResponse;
import com.nhnacademy.front.purchase.purchase.service.PurchaseDetailManagerService;
import com.nhnacademy.front.refund.feign.RefundControllerClient;
import com.nhnacademy.front.refund.feign.RefundRecordMemberControllerClient;

import lombok.RequiredArgsConstructor;

/**
 * 관리자 주문내역 조회 controller
 *
 * @author 정주혁
 *
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class PurchaseDetailManagerController {

	private final PurchaseDetailManagerService purchaseDetailManagerService;
	private final RefundControllerClient refundControllerClient;
	private final RefundRecordMemberControllerClient refundRecordMemberControllerClient;

	/**
	 * 모든 주문 내역 조회
	 *
	 * @param page 주문내역 현재 페이지
	 * @param size 한번에 보여줄 주문내역
	 * @param sort 정렬
	 * @param model
	 * @return 모든 주문내역 사이트로 이동
	 */
	@GetMapping
	public String purchaseDetailManager(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(required = false) String sort,
		Model model

	) {
		Page<ReadPurchaseResponse> responses = purchaseDetailManagerService.readPurchase(page, size, sort);
		model.addAttribute("orderAll", responses);
		return "purchase/order-manager";
	}

	/**
	 * 관리자가 각 주문별 상태 변환하는 코드
	 *
	 * @param orderNumber 주문 내역
	 * @param status 주문 상태
	 * @param model
	 * @return 관리자 주문조회 페이지로 리디렉트
	 */
	@PostMapping("/{orderNumber}")
	public String purchaseDetailManager(@PathVariable String orderNumber, @RequestParam("dropdown") String status, Model model) {
		purchaseDetailManagerService.updatePurchaseStatus(orderNumber,status);
		return "redirect:/admin/orders";
	}



	@PostMapping("/refund/success/{refundRecord}")
	public String refundSuccessManager(@PathVariable Long refundRecord) {
		refundControllerClient.updateSuccessRefund(refundRecord);
		return "redirect:/admin/orders";
	}







}
