package com.nhnacademy.front.purchase.mypage.controller;

import com.nhnacademy.front.purchase.mypage.dto.response.ReadPurchaseCouponDetailResponse;
import com.nhnacademy.front.purchase.mypage.service.PurchaseCouponDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CouponMyPageController {
    private final PurchaseCouponDetailService purchaseCouponDetailService;
    @GetMapping("/orders/coupons")
    public String getCoupons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model){
        Page<ReadPurchaseCouponDetailResponse> responses = purchaseCouponDetailService.readPurchaseCouponForClient(page, size);

        model.addAttribute("responses",responses);

        return "purchase/mypage/coupon-detail";
    }
}
