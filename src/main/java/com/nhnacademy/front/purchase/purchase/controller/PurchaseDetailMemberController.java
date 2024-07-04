package com.nhnacademy.front.purchase.purchase.controller;

import java.util.HashMap;
import java.util.Map;

import com.nhnacademy.front.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchase;
import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.purchase.purchase.service.PurchaseDetailMemberService;
import com.nhnacademy.util.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PurchaseDetailMemberController {
    private final PurchaseDetailMemberService purchaseMemberService;


    @GetMapping("/order/members")
    public String orderDetail(Model model){
        Long memberId = 1L;

        Page<ReadPurchase> orderDetail = purchaseMemberService.readPurchases(memberId);

        model.addAttribute("orders", orderDetail);

        return "purchase/order-detail";
    }

    @GetMapping("/order/members/books/{purchaseId}")
    public String orderDetailBooks(
        @PathVariable(value = "purchaseId") Long purchaseId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "30") int size,
        @RequestParam(required = false) String search,
        Model model) {
        Page<ReadPurchaseBookResponse> orderDetailBooks = purchaseMemberService.readPurchaseBookResponses(purchaseId, page,
            size, search);
        PurchaseStatus purchaseStatus = purchaseMemberService.readPurchaseStatus(purchaseId);
        model.addAttribute("books", orderDetailBooks);
        model.addAttribute("purchaseStatus", purchaseStatus.toString());

        return "purchase/order-detail-book";
    }

    @PostMapping("/order/members/{purchaseId}")
    public String orderConfirmed(@PathVariable(name = "purchaseId") long purchaseId){
        purchaseMemberService.updatePurchaseStatus(purchaseId);

        return "redirect:/order/members";
    }
    @GetMapping("/order/check")
    public ApiResponse<Map<String, Boolean>> checkMemberId(@RequestHeader(value = "member-id", required = false) Long memberId) {
        Map<String, Boolean> response = new HashMap<>();
        if (memberId != null) {
            response.put("isLoggedIn", true);
        } else {
            response.put("isLoggedIn", false);
        }
        return ApiResponse.success(response);
    }


}
