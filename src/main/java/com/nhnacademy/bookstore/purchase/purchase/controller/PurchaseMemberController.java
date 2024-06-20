package com.nhnacademy.bookstore.purchase.purchase.controller;

import com.nhnacademy.bookstore.purchase.purchase.dto.request.CreatePurchaseRequest;
import com.nhnacademy.bookstore.purchase.purchase.dto.request.UpdatePurchaseRequest;
import com.nhnacademy.bookstore.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.bookstore.purchase.purchase.exception.PurchaseFormArgumentErrorException;
import com.nhnacademy.bookstore.purchase.purchase.service.PurchaseService;
import com.nhnacademy.bookstore.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PurchaseMemberController {
    private final PurchaseService purchaseService;

    @GetMapping("members/purchases/{purchaseId}")
    public ApiResponse<ReadPurchaseResponse> readPurchase (@RequestHeader("Member-Id") Long memberId,
                                                           @PathVariable("purchaseId") Long purchaseId) {
        ReadPurchaseResponse response = purchaseService.readPurchase(memberId, purchaseId);

        return new ApiResponse<ReadPurchaseResponse>(
                new ApiResponse.Header(true, 200, "read purchase"),
                new ApiResponse.Body<>(response)
        );
    }

    @PostMapping("members/purchases/")
    public ApiResponse<Void> createPurchase (@RequestHeader("Member-Id") Long memberId,
                                         @Valid @RequestBody CreatePurchaseRequest createPurchaseRequest,
                                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new PurchaseFormArgumentErrorException(bindingResult.getFieldErrors().toString());
        }

        purchaseService.createPurchase(createPurchaseRequest, memberId);

        return new ApiResponse<Void>(new ApiResponse.Header(true, 201, ""));
    }

    @PutMapping("members/purchases/{purchaseId}")
    public ApiResponse<Void> updatePurchaseStatus (@RequestHeader("Member-Id") Long memberId,
                                                   @Valid @RequestBody UpdatePurchaseRequest updatePurchaseRequest,
                                                   BindingResult bindingResult,
                                                   @PathVariable Long purchaseId) {
        if(bindingResult.hasErrors()){
            throw new PurchaseFormArgumentErrorException(bindingResult.getFieldErrors().toString());
        }
        purchaseService.updatePurchase(updatePurchaseRequest, memberId, purchaseId);

        return new ApiResponse<>(new ApiResponse.Header(true, 200, ""));
    }

    @DeleteMapping("members/purchases/{purchaseId}")
    public ApiResponse<Void> deletePurchases (@RequestHeader("Member-Id") Long memberId,
                                                   @PathVariable Long purchaseId) {

        purchaseService.deletePurchase(memberId, purchaseId);

        return new ApiResponse<>(new ApiResponse.Header(true, 204, ""));
    }

}
