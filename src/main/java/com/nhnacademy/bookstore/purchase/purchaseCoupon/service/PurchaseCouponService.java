package com.nhnacademy.bookstore.purchase.purchaseCoupon.service;

import com.nhnacademy.bookstore.purchase.purchaseCoupon.dto.ReadPurchaseCouponResponse;

import java.util.List;

public interface PurchaseCouponService {
    Long create(Long purchaseId, Long couponFormId, int discountPrice);
    List<ReadPurchaseCouponResponse> read(Long purchaseId);
}
