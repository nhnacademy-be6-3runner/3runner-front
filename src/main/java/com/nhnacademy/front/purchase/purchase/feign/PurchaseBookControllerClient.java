package com.nhnacademy.front.purchase.purchase.feign;

import com.nhnacademy.front.purchase.purchase.dto.purchase.request.CreatePurchaseBookRequest;
import com.nhnacademy.front.purchase.purchase.dto.purchase.request.DeletePurchaseBookRequest;
import com.nhnacademy.front.purchase.purchase.dto.purchase.request.UpdatePurchaseBookRequest;
import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.util.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "purchaseBookClient", url = "${feign.client.url}")
public interface PurchaseBookControllerClient {

	@PostMapping("/bookstore/purchases/books")
	ApiResponse<Long> createPurchaseBook(@RequestBody @Valid CreatePurchaseBookRequest createPurchaseBookRequest
	);

	@PutMapping("/bookstore/purchases/books")
	ApiResponse<Long> updatePurchaseBook(@RequestBody @Valid UpdatePurchaseBookRequest updatePurchaseBookRequest
	);

	@DeleteMapping("/bookstore/purchases/books")
	ApiResponse<Void> deletePurchaseBook(@RequestBody @Valid DeletePurchaseBookRequest deletePurchaseBookRequest
	);

	@GetMapping("/bookstore/purchases/books/{purchaseId}")
	ApiResponse<List<ReadPurchaseBookResponse>> readPurchaseBook(
		@PathVariable(value = "purchaseId") Long purchaseId);

	@GetMapping("/bookstore/purchases/books/guests/{purchaseId}")
	ApiResponse<List<ReadPurchaseBookResponse>> readGuestPurchaseBook(
		@PathVariable(value = "purchaseId") String purchaseId);

}