package com.nhnacademy.front.purchase.mypage.service.impl;

import com.nhnacademy.front.purchase.mypage.dto.response.ReadPurchaseCouponDetailResponse;
import com.nhnacademy.front.purchase.mypage.feign.CouponRegisterControllerClient;
import com.nhnacademy.front.purchase.mypage.feign.PurchaseCouponControllerClient;
import com.nhnacademy.front.purchase.mypage.service.PurchaseCouponDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseCouponDetailServiceImpl implements PurchaseCouponDetailService {
    private final PurchaseCouponControllerClient purchaseCouponControllerClient;
    private final CouponRegisterControllerClient couponRegisterControllerClient;

    @Override
    public Page<ReadPurchaseCouponDetailResponse> readPurchaseCouponForClient(int page, int size) {
        return purchaseCouponControllerClient.readPurchaseCoupons(page, size).getBody().getData();
    }

    @Override
    public Boolean getBookCoupon(Long bookId) {
        return couponRegisterControllerClient.registerCouponBook(bookId).getBody().getData();
    }
}
