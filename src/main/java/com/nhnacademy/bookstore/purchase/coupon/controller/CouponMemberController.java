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

/**
 * 맴버 쿠폰 컨트롤러.
 *
 * @author 김병우
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookstore/members")
public class CouponMemberController {
    private final CouponMemberService couponMemberService;

    /**
     * 맴버 쿠폰 전체 읽기.
     *
     * @param memberId 맴버아이디
     * @return 쿠폰폼dto 리스트
     */
    @GetMapping("/coupons")
    private ApiResponse<List<ReadCouponFormResponse>> readCoupons(
            @RequestHeader("Member-Id") Long memberId) {

        return ApiResponse.success(couponMemberService.readMemberCoupons(memberId));
    }

}
