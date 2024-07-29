package com.nhnacademy.front.refund.feign;

import com.nhnacademy.front.refund.dto.request.CreateRefundRecordRedisRequest;
import com.nhnacademy.front.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "refundRecordGuestControllerClient", url = "${feign.client.url}")
public interface RefundRecordGuestControllerClient {
	@PostMapping("/bookstore/refundRecord/guests/{orderNumber}/{purchaseBookId}")
	ApiResponse<Long> createRefundRecordGuestRedis(
		@PathVariable(name = "orderNumber") String orderNumber,
		@PathVariable(name = "purchaseBookId") Long purchaseBookId,
		@RequestBody @Valid CreateRefundRecordRedisRequest createRefundRecordRequest);

	@PutMapping("/bookstore/refundRecord/guests/{orderNumber}/{purchaseBookId}")
	ApiResponse<Long> updateRefundRecordGuest(
		@PathVariable(name = "orderNumber") String orderNumber,
		@PathVariable(name = "purchaseBookId") Long purchaseBookId,
		@RequestParam(name = "quantity") int quantity
	);

	@DeleteMapping("/bookstore/refundRecord/guests/{orderNumber}/{purchaseBookId}")
	ApiResponse<Long> deleteRefundRecordGuest(
		@PathVariable(name = "orderNumber") String orderNumber,
		@PathVariable(name = "purchaseBookId") Long purchaseBookId
	);

	@PostMapping("/bookstore/refundRecord/guests/save/{orderNumber}/{refundId}")
	ApiResponse<Boolean> createRefundRecordGuest(
		@PathVariable(name = "orderNumber") String orderNumber,
		@PathVariable(name = "refundId") Long refundId);

	@PutMapping("/bookstore/refundRecord/guests/all/{orderNumber}")
	ApiResponse<Boolean> updateRefundRecordAllGuest(@PathVariable(name = "orderNumber") String orderNumber);

	@PutMapping("/bookstore/refundRecord/guests/all/zero/{orderNumber}")
	ApiResponse<Boolean> updateRefundRecordAllZeroGuest(
		@PathVariable(name = "orderNumber") String orderNumber
	);
}
