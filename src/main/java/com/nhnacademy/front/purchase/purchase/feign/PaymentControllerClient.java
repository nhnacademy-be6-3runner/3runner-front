package com.nhnacademy.front.purchase.purchase.feign;

import org.json.simple.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PaymentControllerClient", url = "${feign.client.url}")
public interface PaymentControllerClient {
	@RequestMapping(value = "/bookstore/payments/guests/confirm")
	ResponseEntity<JSONObject> confirmGuestPayment(
		@RequestParam(required = false) Long cartId,
		@RequestParam(required = false) String address,
		@RequestParam(required = false) String password,
		@RequestParam(required = false) String isPacking,
		@RequestParam(required = false) String shipping,
		@RequestBody String jsonBody) throws Exception;

	@RequestMapping(value = "/bookstore/payments/members/confirm")
	ResponseEntity<JSONObject> confirmMemberPayment(
		@RequestParam(required = false) String discountedPrice,
		@RequestParam(required = false) String discountedPoint,
		@RequestParam(required = false) String isPacking,
		@RequestParam(required = false) String shipping,
		@RequestParam(required = false) String road,
		@RequestParam(required = false) Long couponFormId,
		@RequestBody String jsonBody) throws Exception;

}
