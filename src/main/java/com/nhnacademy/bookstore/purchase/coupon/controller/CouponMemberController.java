package com.nhnacademy.bookstore.purchase.coupon.controller;

import com.nhnacademy.bookstore.purchase.coupon.feign.dto.response.ReadCouponFormResponse;
import com.nhnacademy.bookstore.purchase.coupon.service.CouponMemberService;
import com.nhnacademy.bookstore.util.ApiResponse;
import jakarta.ws.rs.GET;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookstore/members")
public class CouponMemberController {
    private final CouponMemberService couponMemberService;

    @GetMapping("/coupons")
    private ApiResponse<List<ReadCouponFormResponse>> readCoupons(
            @RequestHeader("Member-Id") Long memberId) {

        return ApiResponse.success(couponMemberService.readMemberCoupons(memberId));
    }

}
