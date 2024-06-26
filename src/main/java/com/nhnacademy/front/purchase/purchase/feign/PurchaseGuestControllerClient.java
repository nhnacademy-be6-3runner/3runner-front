package com.nhnacademy.front.purchase.purchase.feign;

import com.nhnacademy.front.purchase.purchase.dto.request.CreatePurchaseRequest;
import com.nhnacademy.front.purchase.purchase.dto.request.ReadDeletePurchaseGuestRequest;
import com.nhnacademy.front.purchase.purchase.dto.request.UpdatePurchaseGuestRequest;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "purchaseGuestControllerClient", url = "http://localhost:8080")
public interface PurchaseGuestControllerClient {
    @GetMapping("/bookstore/guests/purchases")
    ApiResponse<ReadPurchaseResponse> readPurchase (@Valid @RequestBody ReadDeletePurchaseGuestRequest readPurchaseRequest, BindingResult bindingResult);

    @PostMapping("/bookstore/guests/purchases")
    ApiResponse<Void> createPurchase (@Valid @RequestBody CreatePurchaseRequest createPurchaseRequest, BindingResult bindingResult);

    @PutMapping("/bookstore/guests/purchases")
    ApiResponse<Void> updatePurchaseStatus (@Valid @RequestBody UpdatePurchaseGuestRequest updatePurchaseGuestRequest, BindingResult bindingResult);

    @DeleteMapping("/bookstore/guests/purchases")
    ApiResponse<Void> deletePurchases (@Valid @RequestBody ReadDeletePurchaseGuestRequest readDeletePurchaseGuestRequest, BindingResult bindingResult);
}
