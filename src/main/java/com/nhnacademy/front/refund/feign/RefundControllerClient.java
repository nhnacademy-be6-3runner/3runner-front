package com.nhnacademy.front.refund.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.refund.dto.request.CreateRefundRequest;
import com.nhnacademy.front.refund.dto.response.ReadRefundResponse;
import com.nhnacademy.front.util.ApiResponse;

import jakarta.validation.Valid;

@FeignClient(name = "refundControllerClient", url = "${feign.client.url}")
public interface RefundControllerClient {

	@GetMapping("/bookstore/refund/{purchaseId}")
	ApiResponse<String> readTossOrderId(@PathVariable("purchaseId") String purchaseId);

	@GetMapping("/bookstore/refund/member/{purchaseId}")
	ApiResponse<String> readTossOrderIdMember(@PathVariable("purchaseId") Long purchaseId);

	@GetMapping("/bookstore/refund/managers/{refundRecord}")
	ApiResponse<ReadRefundResponse> readRefundRecord(@PathVariable("refundRecord") Long refundRecord);

	@PostMapping("/bookstore/refund/{orderId}")
	ApiResponse<Long> createRefund(@PathVariable("orderId") Long orderId,
		@RequestBody @Valid CreateRefundRequest createRefundRequest);

	@PutMapping("/bookstore/refund/success/{refundRecord}")
	ApiResponse<Boolean> updateSuccessRefund(@PathVariable("refundRecord") Long refundRecord);

	@PutMapping("/bookstore/refund/reject/{refundRecord}")
	ApiResponse<Boolean> updateRefundRejected(@PathVariable("refundRecord") Long refundRecord);

	@PostMapping("/bookstore/refund/cancel/payment/part/{orderNumber}")
	ApiResponse<Long> createRefundCancelPartPayment(@PathVariable("orderNumber") Object orderNumber,
		@RequestParam(name = "price") Integer price);

	@GetMapping("/bookstore/refund/managers/all")
	ApiResponse<List<ReadRefundResponse>> readAllRefund();
}
