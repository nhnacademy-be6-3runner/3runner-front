package com.nhnacademy.front.purchase.purchase.controller;

import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchase;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseMemberControllerClient;
import com.nhnacademy.front.purchase.purchase.service.PurchaseDetailMemberService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PurchaseDetailMemberController {
    private final PurchaseMemberControllerClient purchaseMemberControllerClient;
    private final PurchaseDetailMemberService purchaseMemberService;

    @GetMapping("/order")
    public String order(Model model){
        return "order";
    }

    @GetMapping("/order/complete")
    public String orderComplete(){
        return "order-complete";
    }

    @GetMapping("/bookstore/members/purchases")
    public String orderDetail(Model model){
        // Long memberId = Long.parseLong(request.getHeader("Member-Id"));
        Long memberId = 1L;
        Page<ReadPurchase> orderDetail = purchaseMemberService.readPurchases(memberId);

        model.addAttribute("orders", orderDetail);

        return "order-detail";
    }


}
