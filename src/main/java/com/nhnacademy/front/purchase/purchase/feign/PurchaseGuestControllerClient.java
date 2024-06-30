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

@FeignClient(name = "purchaseGuestControllerClient", url = "http://${feign.client.url}")
public interface PurchaseGuestControllerClient {
    @GetMapping("/bookstore/guests/purchases")
    ApiResponse<ReadPurchaseResponse> readPurchase (@Valid @RequestBody ReadDeletePurchaseGuestRequest readPurchaseRequest);

    @PostMapping("/bookstore/guests/purchases")
    ApiResponse<Void> createPurchase (@Valid @RequestBody CreatePurchaseRequest createPurchaseRequest);

    @PutMapping("/bookstore/guests/purchases")
    ApiResponse<Void> updatePurchaseStatus (@Valid @RequestBody UpdatePurchaseGuestRequest updatePurchaseGuestRequest);

    @DeleteMapping("/bookstore/guests/purchases")
    ApiResponse<Void> deletePurchases (@Valid @RequestBody ReadDeletePurchaseGuestRequest readDeletePurchaseGuestRequest);
}
