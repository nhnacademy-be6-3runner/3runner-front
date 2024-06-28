package com.nhnacademy.front.purchase.purchase.feign;

import com.nhnacademy.front.purchase.purchase.dto.request.CreatePurchaseRequest;
import com.nhnacademy.front.purchase.purchase.dto.request.ReadDeletePurchaseGuestRequest;
import com.nhnacademy.front.purchase.purchase.dto.request.UpdatePurchaseGuestRequest;
import com.nhnacademy.front.purchase.purchase.dto.request.UpdatePurchaseMemberRequest;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "purchaseMemberControllerClient", url = "http://133.186.143.40")
public interface PurchaseMemberControllerClient {
    @GetMapping("/members/purchases/{purchaseId}")
    ApiResponse<ReadPurchaseResponse> readPurchase (@RequestHeader("Member-Id") Long memberId, @PathVariable(value = "purchaseId", required = false) Long purchaseId);

    @GetMapping("/bookstore/members/purchases")
    ApiResponse<List<ReadPurchaseResponse>> readPurchases (@RequestHeader("Member-Id") Long memberId);

    @PostMapping("/bookstore/members/purchases")
    ApiResponse<Void> createPurchase (@RequestHeader("Member-Id") Long memberId, @Valid @RequestBody CreatePurchaseRequest createPurchaseRequest);

    @PutMapping("/bookstore/members/purchases/{purchaseId}")
    ApiResponse<Void> updatePurchaseStatus (@RequestHeader("Member-Id") Long memberId, @Valid @RequestBody UpdatePurchaseMemberRequest updatePurchaseRequest,@PathVariable Long purchaseId);

    @DeleteMapping("/bookstore/members/purchases/{purchaseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ApiResponse<Void> deletePurchases (@RequestHeader("Member-Id") Long memberId, @PathVariable Long purchaseId);
}
