package com.nhnacademy.front.purchase.purchase.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.front.purchase.purchase.dto.request.UpdatePurchaseMemberRequest;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.front.purchase.purchase.service.PurchaseDetailManagerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager/purchases")
public class PurchaseDetailManagerController {

	private final PurchaseDetailManagerService purchaseDetailManagerService;

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








}
