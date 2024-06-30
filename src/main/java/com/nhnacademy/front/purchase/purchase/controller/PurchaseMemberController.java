package com.nhnacademy.front.purchase.purchase.controller;

import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchase;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseMemberControllerClient;
import com.nhnacademy.front.purchase.purchase.service.PurchaseMemberService;
import com.nhnacademy.front.purchase.purchase.service.impl.PurchaseMemberServiceImpl;
import com.nhnacademy.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class PurchaseMemberController {
    private final PurchaseMemberControllerClient purchaseMemberControllerClient;
    private final PurchaseMemberService purchaseMemberService;

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
