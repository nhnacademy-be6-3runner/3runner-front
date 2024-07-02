package com.nhnacademy.bookstore.purchase.purchase.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.bookstore.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.bookstore.purchase.purchase.service.PurchaseManagerService;
import com.nhnacademy.bookstore.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookstore/managers/purchases")
public class PurchaseManagerController {
	private final PurchaseManagerService purchaseManagerService;

	@GetMapping
	public ApiResponse<Page<ReadPurchaseResponse>> readPurchases(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(required = false) String sort
	) {
		Pageable pageable;
		if (Objects.isNull(sort)) {
			pageable = PageRequest.of(page, size);
		} else {
			pageable = PageRequest.of(page, size, Sort.by(sort));
		}
		List<ReadPurchaseResponse> purchaseResponses = purchaseManagerService.readPurchaseAll();

		return ApiResponse.success(new PageImpl<>(purchaseResponses, pageable, purchaseResponses.size()));
	}

	@PutMapping("/{purchaseId}")
	public ApiResponse<Long> purchaseUpdate(@RequestHeader(value = "Member-Id") long memberId,
		@PathVariable(value = "purchaseId") String purchaseId,
		@RequestParam String purchaseStatus) {
		return ApiResponse.success(purchaseManagerService.updatePurchaseStatus(memberId, purchaseId, purchaseStatus));
	}
}
