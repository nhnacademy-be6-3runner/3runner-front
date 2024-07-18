package com.nhnacademy.front.purchase.mypage.service.impl;

import com.nhnacademy.front.purchase.mypage.dto.request.ReadPointRecordRequest;
import com.nhnacademy.front.purchase.mypage.dto.response.ReadPointRecordResponse;
import com.nhnacademy.front.purchase.mypage.feign.PointRecordControllerClient;
import com.nhnacademy.front.purchase.mypage.service.PointDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointDetailServiceImpl implements PointDetailService {
    private final PointRecordControllerClient pointRecordControllerClient;
    @Override
    public Page<ReadPointRecordResponse> readPurchaseCouponForClient(ReadPointRecordRequest readPointRecordRequest) {
        return pointRecordControllerClient.readPointRecord(readPointRecordRequest).getBody().getData();
    }
}
