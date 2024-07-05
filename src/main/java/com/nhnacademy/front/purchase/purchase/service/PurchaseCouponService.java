package com.nhnacademy.front.purchase.purchase.service;

import com.nhnacademy.front.purchase.cart.dto.response.ReadAllBookCartMemberResponse;
import com.nhnacademy.front.purchase.purchase.dto.coupon.CouponDiscountPriceDto;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponFormResponse;
import com.nhnacademy.front.util.ApiResponse;

import java.util.List;

public interface PurchaseCouponService {
    List<CouponDiscountPriceDto> getValidCoupons(List<ReadAllBookCartMemberResponse> items,
                                                 ApiResponse.Body<List<ReadCouponFormResponse>> response);
}
