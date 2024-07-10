package com.nhnacademy.front.purchase.mypage.controller;

import com.nhnacademy.front.purchase.mypage.dto.request.CouponRegistorRequest;
import com.nhnacademy.front.purchase.mypage.dto.response.ReadPurchaseCouponDetailResponse;
import com.nhnacademy.front.purchase.mypage.feign.CouponRegisterControllerClient;
import com.nhnacademy.front.purchase.mypage.service.PurchaseCouponDetailService;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponFormResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CouponMyPageController {
    private final PurchaseCouponDetailService purchaseCouponDetailService;
    private final CouponRegisterControllerClient couponMemberControllerClient;

    @GetMapping("/mypages/coupons")
    public String getCoupons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model){
        Page<ReadPurchaseCouponDetailResponse> responses = purchaseCouponDetailService.readPurchaseCouponForClient(page, size);
        List<ReadCouponFormResponse> usableCoupons = couponMemberControllerClient.readCoupons().getBody().getData();

        model.addAttribute("responses",responses);
        model.addAttribute("usableCoupons", usableCoupons);
        return "purchase/mypage/coupon-detail";
    }

    @PostMapping("/mypages/coupons")
    public String postRegistorCoupons(@Valid @ModelAttribute CouponRegistorRequest couponRegistorRequest,
                                      BindingResult bindingResult){

        couponMemberControllerClient.registerCoupon(couponRegistorRequest);

        return "redirect:/mypages/coupons";
    }

    @GetMapping("/mypages/coupons/registers")
    public String getRegistorCouponsCoupons(){

        return "purchase/mypage/coupon-form";
    }

}
