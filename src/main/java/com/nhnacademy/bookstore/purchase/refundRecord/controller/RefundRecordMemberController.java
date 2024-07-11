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
import com.nhnacademy.bookstore.purchase.refundRecord.service.RefundRecordMemberService;
import com.nhnacademy.bookstore.util.ApiResponse;
import com.nhnacademy.bookstore.util.ValidationUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookstore/refundRecord/members")
public class RefundRecordMemberController {

	private final RefundRecordMemberService refundRecordMemberService;

	@PostMapping("/{purchaseBookId}")
	public ApiResponse<Long> createRefundRecordGuestRedis(
		@RequestHeader("Member-Id") Long memberId,
		@PathVariable(name = "purchaseBookId") Long purchaseBookId,
		@RequestBody @Valid CreateRefundRecordRedisRequest createRefundRecordRequest,
		BindingResult bindingResult) {

		ValidationUtils.validateBindingResult(bindingResult, new CreateRefundRecordRedisRequestFormException());
		return ApiResponse.createSuccess(refundRecordMemberService
			.createRefundRecordRedis(memberId, purchaseBookId,
				createRefundRecordRequest.price(), createRefundRecordRequest.quantity(),
				createRefundRecordRequest.readBookByPurchase()));
	}

	@PutMapping("/{purchaseBookId}")
	public ApiResponse<Long> updateRefundRecordMember(
		@RequestHeader("Member-Id") Long memberId,
		@PathVariable(name = "purchaseBookId") Long purchaseBookId,
		@RequestParam(name = "quantity") int quantity) {

		return ApiResponse.success(refundRecordMemberService.updateRefundRecordRedis(memberId,
			purchaseBookId, quantity));
	}

	@DeleteMapping("/{purchaseBookId}")
	public ApiResponse<Long> deleteRefundRecordGuest(
		@RequestHeader("Member-Id") Long memberId,
		@PathVariable(name = "purchaseBookId") Long purchaseBookId
	) {
		return ApiResponse.deleteSuccess(
			refundRecordMemberService.deleteRefundRecordRedis(memberId,
				purchaseBookId));
	}

	@PostMapping("/save/{refundId}")
	public ApiResponse<Boolean> createRefundRecordGuest(
		@RequestHeader("Member-Id") Long memberId,
		@PathVariable(name = "refundId") Long refundId
	) {
		return ApiResponse.createSuccess(
			refundRecordMemberService.createRefundRecord(memberId, refundId));
	}

	@PutMapping("/all/{orderNumber}")
	ApiResponse<Long> updateRefundRecordAllMember(
		@RequestHeader("Member-Id") Long memberId,
		@PathVariable(name = "orderNumber") Long orderNumber
	) {
		return ApiResponse.success(
			refundRecordMemberService.updateRefundRecordAllRedis(memberId, orderNumber));
	}

	@PutMapping("/all/zero/{orderNumber}")
	ApiResponse<Long> updateRefundRecordAllZeroMember(
		@RequestHeader("Member-Id") Long memberId,
		@PathVariable(name = "orderNumber") Long orderNumber
	) {
		return ApiResponse.success(
			refundRecordMemberService.updateRefundRecordZeroAllRedis(memberId, orderNumber));
	}

}
