package com.nhnacademy.bookstore.purchase.refundRecord.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.bookstore.purchase.refundRecord.dto.request.CreateRefundRecordRedisRequest;
import com.nhnacademy.bookstore.purchase.refundRecord.exception.CreateRefundRecordRedisRequestFormException;
import com.nhnacademy.bookstore.purchase.refundRecord.service.RefundRecordGuestService;
import com.nhnacademy.bookstore.util.ApiResponse;
import com.nhnacademy.bookstore.util.ValidationUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookstore/refundRecord/guests")
public class RefundRecordGuestController {
	private final RefundRecordGuestService refundRecordGuestService;

	@PostMapping("/{orderNumber}/{purchaseBookId}")
	public ApiResponse<Long> createRefundRecordGuestRedis(
		@PathVariable(name = "orderNumber") String orderNumber,
		@PathVariable(name = "purchaseBookId") Long purchaseBookId,
		@RequestBody @Valid CreateRefundRecordRedisRequest createRefundRecordRequest,
		BindingResult bindingResult) {

		ValidationUtils.validateBindingResult(bindingResult, new CreateRefundRecordRedisRequestFormException());
		return ApiResponse.createSuccess(refundRecordGuestService
			.createRefundRecordRedis(orderNumber, purchaseBookId,
				createRefundRecordRequest.price(), createRefundRecordRequest.quantity(),
				createRefundRecordRequest.readBookByPurchase()));
	}

	@PutMapping("/{orderNumber}/{purchaseBookId}")
	public ApiResponse<Long> updateRefundRecordGuest(
		@PathVariable(name = "orderNumber") String orderNumber,
		@PathVariable(name = "purchaseBookId") Long purchaseBookId,
		@RequestParam(name = "quantity") int quantity
	) {
		return ApiResponse.success(refundRecordGuestService
			.updateRefundRecordRedis(orderNumber, purchaseBookId, quantity));
	}

	@DeleteMapping("/{orderNumber}/{purchaseBookId}")
	public ApiResponse<Long> deleteRefundRecordGuest(
		@PathVariable(name = "orderNumber") String orderNumber,
		@PathVariable(name = "purchaseBookId") Long purchaseBookId
	) {
		return ApiResponse.deleteSuccess(
			refundRecordGuestService.deleteRefundRecordRedis(orderNumber, purchaseBookId));
	}

	@PostMapping("/save/{orderNumber}/{refundId}")
	public ApiResponse<Boolean> createRefundRecordGuest(
		@PathVariable(name = "orderNumber") String orderNumber,
		@PathVariable(name = "refundId") Long refundId) {
		return ApiResponse.createSuccess(
			refundRecordGuestService.createRefundRecord(orderNumber, refundId));
	}

	@PutMapping("/all/{orderNumber}")
	ApiResponse<Boolean> updateRefundRecordAllMember(
		@PathVariable(name = "orderNumber") String orderNumber
	) {
		return ApiResponse.success(
			refundRecordGuestService.updateRefundRecordAllRedis(orderNumber)
		);
	}

	@PutMapping("/all/zero/{orderNumber}")
	ApiResponse<Boolean> updateRefundRecordAllZeroMember(
		@PathVariable(name = "orderNumber") String orderNumber
	) {
		return ApiResponse.success(
			refundRecordGuestService.updateRefundRecordZeroAllRedis(orderNumber));
	}

}
