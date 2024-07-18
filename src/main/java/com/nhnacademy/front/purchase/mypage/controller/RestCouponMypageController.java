package com.nhnacademy.front.purchase.mypage.controller;

import com.nhnacademy.front.purchase.mypage.feign.CouponRegisterControllerClient;
import com.nhnacademy.front.purchase.mypage.service.PurchaseCouponDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestCouponMypageController {
    private final CouponRegisterControllerClient couponMemberControllerClient;

    @PostMapping("/coupons/books/{bookId}")
    public String autoRegistorCoupons(@PathVariable Long bookId){
        return couponMemberControllerClient.registerCouponBook(bookId).getBody().getData().toString();
    }
}
