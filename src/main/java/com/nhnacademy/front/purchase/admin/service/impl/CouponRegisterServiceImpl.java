package com.nhnacademy.front.purchase.admin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.front.purchase.admin.service.CouponRegisterService;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateCouponFormRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponRegisterServiceImpl implements CouponRegisterService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private static final String queueName2 = "3RUNNER-COUPON-EXPIRED-IN-THREE-DAY";
    private static final String queueName1 = "3RUNNER-COUPON-ISSUED";

    @Override
    public void creatWithMq(CreateCouponFormRequest readCouponFormRequest, Long quantity) throws JsonProcessingException  {
        for(int i = 0 ; i < quantity ; i++) {
            rabbitTemplate.convertAndSend(queueName1, objectMapper.writeValueAsString(readCouponFormRequest));
        }
    }
}
