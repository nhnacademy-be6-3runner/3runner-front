package com.nhnacademy.front.purchase.mypage.service;

import com.nhnacademy.front.purchase.mypage.dto.response.ReadPurchaseCouponDetailResponse;
import org.springframework.data.domain.Page;

public interface PurchaseCouponDetailService{

    Page<ReadPurchaseCouponDetailResponse> readPurchaseCouponForClient(int page, int size);

    Boolean getBookCoupon(Long bookId);
}
