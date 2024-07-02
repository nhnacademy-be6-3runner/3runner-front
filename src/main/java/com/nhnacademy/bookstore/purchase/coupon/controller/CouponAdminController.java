package com.nhnacademy.bookstore.purchase.coupon.controller;

import com.nhnacademy.bookstore.member.member.dto.response.GetMemberResponse;
import com.nhnacademy.bookstore.member.member.dto.response.ReadMemberResponse;
import com.nhnacademy.bookstore.member.member.service.MemberPointService;
import com.nhnacademy.bookstore.purchase.coupon.feign.dto.request.CreateCouponFormRequest;
import com.nhnacademy.bookstore.purchase.coupon.feign.dto.response.ReadCouponTypeResponse;
import com.nhnacademy.bookstore.purchase.coupon.feign.dto.response.ReadCouponUsageResponse;
import com.nhnacademy.bookstore.purchase.coupon.service.CouponAdminService;
import com.nhnacademy.bookstore.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookstore/admin")
public class CouponAdminController {
    private final CouponAdminService couponAdminService;
    private final MemberPointService memberPointService;

    @GetMapping("/coupons/types")
    public ApiResponse<List<ReadCouponTypeResponse>> getTypes() {
        return ApiResponse.success(couponAdminService.readTypes());
    }

    @GetMapping("/coupons/usages")
    public ApiResponse<List<ReadCouponUsageResponse>> getUsages() {
        return ApiResponse.success(couponAdminService.readUsages());
    }

    @GetMapping("/members")
    public ApiResponse<List<ReadMemberResponse>> getMembers() {
        return ApiResponse.success(memberPointService.readAll());
    }

    @PostMapping("/coupons/{targetMemberId}")
    public ApiResponse<Long> createCoupon(
            @PathVariable Long targetMemberId,
            @RequestBody CreateCouponFormRequest createCouponFormRequest) {
        Long response = couponAdminService.createCoupon(createCouponFormRequest,targetMemberId);
        return ApiResponse.createSuccess(response);
    }
}
