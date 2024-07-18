package com.nhnacademy.front.purchase.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateCouponFormRequest;

public interface CouponRegisterService {
    void creatWithMq(CreateCouponFormRequest readCouponFormRequest, Long quantity) throws JsonProcessingException;
}
