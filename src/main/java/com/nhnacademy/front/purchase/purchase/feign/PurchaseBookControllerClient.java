package com.nhnacademy.front.purchase.purchase.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.purchase.purchase.dto.request.CreatePurchaseBookRequest;
import com.nhnacademy.front.purchase.purchase.dto.request.DeletePurchaseBookRequest;
import com.nhnacademy.front.purchase.purchase.dto.request.UpdatePurchaseBookRequest;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseBookResponse;
import com.nhnacademy.util.ApiResponse;

import jakarta.validation.Valid;

@FeignClient(name = "purchaseBookClient", url = "http://localhost:8081")
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
	ApiResponse<Page<ReadPurchaseBookResponse>> readPurchaseBook(
		@PathVariable(value = "purchaseId")  long purchaseId
		, @RequestParam int page
		, @RequestParam int size
		, @RequestParam(required = false) String sort);



}