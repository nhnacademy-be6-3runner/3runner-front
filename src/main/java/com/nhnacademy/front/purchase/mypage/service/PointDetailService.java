package com.nhnacademy.front.purchase.mypage.service;

import com.nhnacademy.front.purchase.mypage.dto.request.ReadPointRecordRequest;
import com.nhnacademy.front.purchase.mypage.dto.response.ReadPointRecordResponse;
import com.nhnacademy.front.purchase.mypage.dto.response.ReadPurchaseCouponDetailResponse;
import org.springframework.data.domain.Page;

public interface PointDetailService {
    Page<ReadPointRecordResponse> readPurchaseCouponForClient(ReadPointRecordRequest readPointRecordRequest);
}
