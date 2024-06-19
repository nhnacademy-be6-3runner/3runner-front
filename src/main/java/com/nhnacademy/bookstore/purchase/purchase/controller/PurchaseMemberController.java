package com.nhnacademy.bookstore.purchase.purchase.controller;

import com.nhnacademy.bookstore.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.bookstore.purchase.purchase.service.PurchaseService;
import com.nhnacademy.bookstore.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PurchaseMemberController {
    private final PurchaseService purchaseService;
    @GetMapping("members/{memberId}/purchases/{purchaseId}")
    public ApiResponse<ReadPurchaseResponse> readPurchase(
            @PathVariable("memberId") Long memberId,
            @PathVariable("purchaseId") Long purchaseId) {


        return new ApiResponse<ReadPurchaseResponse>(
                new ApiResponse.Header(true, 200, "read purchase"),
                new ApiResponse.Body<>())
    }
}
