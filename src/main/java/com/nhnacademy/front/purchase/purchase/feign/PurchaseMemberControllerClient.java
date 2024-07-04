package com.nhnacademy.front.purchase.purchase.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nhnacademy.front.purchase.purchase.dto.request.CreatePurchaseRequest;
import com.nhnacademy.front.purchase.purchase.dto.request.UpdatePurchaseMemberRequest;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.util.ApiResponse;

import jakarta.validation.Valid;

import java.util.List;
@FeignClient(name = "purchaseMemberControllerClient", url = "http://${feign.client.url}")
public interface PurchaseMemberControllerClient {

    @GetMapping("/bookstore/members/purchases/{purchaseId}")
    ApiResponse<ReadPurchaseResponse> readPurchase (@PathVariable(value = "purchaseId", required = false) Long purchaseId);

    @GetMapping("/bookstore/members/purchases")
    ApiResponse<Page<ReadPurchaseResponse>> readPurchases (@RequestParam int page
        , @RequestParam int size
        , @RequestParam(required = false) String sort);

    @PostMapping("/bookstore/members/purchases")
    ApiResponse<Void> createPurchase (@Valid @RequestBody CreatePurchaseRequest createPurchaseRequest);

    @PutMapping("/bookstore/members/purchases/{purchaseId}")
    ApiResponse<Void> updatePurchaseStatus (@Valid @RequestBody UpdatePurchaseMemberRequest updatePurchaseRequest,@PathVariable Long purchaseId);

    @DeleteMapping("/bookstore/members/purchases/{purchaseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ApiResponse<Void> deletePurchases (@PathVariable Long purchaseId);
}
