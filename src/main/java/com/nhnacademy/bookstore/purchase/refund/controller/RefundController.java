package com.nhnacademy.bookstore.purchase.refund.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.bookstore.purchase.refund.dto.request.CreateRefundRequest;
import com.nhnacademy.bookstore.purchase.refund.dto.request.PaymentKeyRequest;
import com.nhnacademy.bookstore.purchase.refund.dto.response.ReadRefundResponse;
import com.nhnacademy.bookstore.purchase.refund.exception.CreateRefundRequestFormException;
import com.nhnacademy.bookstore.purchase.refund.service.RefundService;
import com.nhnacademy.bookstore.util.ApiResponse;
import com.nhnacademy.bookstore.util.ValidationUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookstore/refund")
public class RefundController {
	private final RefundService refundService;

	@GetMapping("/{purchaseId}")
	public ApiResponse<PaymentKeyRequest> readTossOrderId(@PathVariable("purchaseId") String purchaseId) {
		String tossOrderId = refundService.readTossOrderId(purchaseId);
		ApiResponse<PaymentKeyRequest> response = ApiResponse.success(
			PaymentKeyRequest.builder().paymentKey(tossOrderId).build());
		return response;
	}

	@GetMapping("/managers/{refundRecord}")
	public ApiResponse<ReadRefundResponse> readRefundRequest(@PathVariable("refundRecord") Long refundRecord) {
		return ApiResponse.success(refundService.readRefund(refundRecord));
	}

	@PostMapping("/{orderId}")
	public ApiResponse<Long> createRefund(@RequestHeader(name = "Member-Id") Long memberId,
		@PathVariable("orderId") Long orderId,
		@RequestBody @Valid CreateRefundRequest createRefundRequest,
		BindingResult bindingResult) {
		ValidationUtils.validateBindingResult(bindingResult, new CreateRefundRequestFormException());

		return ApiResponse.createSuccess(
			refundService.createRefund(orderId, createRefundRequest.refundContent(), createRefundRequest.price(),memberId));
	}

	@PutMapping("/success/{refundRecord}")
	public ApiResponse<Boolean> updateSuccessRefund(@PathVariable("refundRecord") Long refundId) {
		return ApiResponse.success(refundService.updateSuccessRefund(refundId));
	}

	@PutMapping("/reject/{refundRecord}")
	public ApiResponse<Boolean> updateRefundRejected(@PathVariable("refundRecord") Long refundId) {
		return ApiResponse.success(refundService.updateRefundRejected(refundId));
	}

	@PostMapping("/cancel/payment/{orderNumber}")
	public ApiResponse<Long> createRefundCancelPayment(@RequestHeader(name = "Member-Id") Long memberId, @PathVariable("orderNumber") String orderNumber) {
		return ApiResponse.success(refundService.createRefundCancelPayment(orderNumber));
	}
}
