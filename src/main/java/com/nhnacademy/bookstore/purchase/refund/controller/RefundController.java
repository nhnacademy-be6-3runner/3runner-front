package com.nhnacademy.bookstore.purchase.refund.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstore.purchase.refund.service.RefundService;
import com.nhnacademy.bookstore.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bookstore/refund")
public class RefundController {
	private final RefundService refundService;

	@GetMapping("/{purchaseId}")
	public ApiResponse<String> refund(@PathVariable("purchaseId") String purchaseId, Model model) {
		String tossOrderId = refundService.readTossOrderId(purchaseId);
		return ApiResponse.success(tossOrderId);
	}
}
