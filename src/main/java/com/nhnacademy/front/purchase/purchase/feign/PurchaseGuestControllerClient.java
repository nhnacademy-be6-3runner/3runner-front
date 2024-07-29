package com.nhnacademy.front.purchase.purchase.feign;

import com.nhnacademy.front.purchase.purchase.dto.purchase.request.CreatePurchaseRequest;
import com.nhnacademy.front.purchase.purchase.dto.purchase.request.ReadDeletePurchaseGuestRequest;
import com.nhnacademy.front.purchase.purchase.dto.purchase.request.UpdatePurchaseGuestRequest;
import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseResponse;
import com.nhnacademy.front.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "purchaseGuestControllerClient", url = "${feign.client.url}")
public interface PurchaseGuestControllerClient {
	@GetMapping("/bookstore/guests/purchases")
	ApiResponse<ReadPurchaseResponse> readPurchase(@RequestParam String orderNumber,
		@RequestParam(required = false) String password);

	@PostMapping("/bookstore/guests/purchases")
	ApiResponse<Void> createPurchase(@Valid @RequestBody CreatePurchaseRequest createPurchaseRequest);

	@PutMapping("/bookstore/guests/purchases")
	ApiResponse<Void> updatePurchaseStatus(@Valid @RequestBody UpdatePurchaseGuestRequest updatePurchaseGuestRequest);

	@DeleteMapping("/bookstore/guests/purchases")
	ApiResponse<Void> deletePurchases(
		@Valid @RequestBody ReadDeletePurchaseGuestRequest readDeletePurchaseGuestRequest);

	@GetMapping("/bookstore/guests/purchases/validate")
	ApiResponse<Boolean> validatePurchases(@RequestParam String orderNumber, @RequestParam String password);

}
