package com.nhnacademy.front.purchase.admin.service.impl;

import com.nhnacademy.front.purchase.admin.feign.CouponFormControllerClient;
import com.nhnacademy.front.purchase.admin.feign.CouponMemberControllerClient;
import com.nhnacademy.front.purchase.admin.feign.CouponPolicyControllerClient;
import com.nhnacademy.front.purchase.admin.service.AdminCouponCreateService;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateCouponFormRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponFormResponse;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponTypeResponse;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponUsageResponse;
import com.nhnacademy.front.purchase.purchase.dto.member.response.ReadMemberResponse;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCouponCreateServiceImpl implements AdminCouponCreateService {
    private final CouponFormControllerClient couponFormControllerClient;
    private final CouponPolicyControllerClient couponPolicyControllerClient;
    private final CouponMemberControllerClient couponMemberControllerClient;

    @Override
    public List<ReadCouponUsageResponse> getUsages() {
        return couponPolicyControllerClient.readAllUsages().getBody().getData();
    }

    @Override
    public List<ReadCouponTypeResponse> getTypes() {
        return couponPolicyControllerClient.readAllTypes().getBody().getData();
    }

    @Override
    public Long createCouponForm(CreateCouponFormRequest createCouponFormRequest, Long targetMemberId) {
        return couponMemberControllerClient.createCoupon(targetMemberId,createCouponFormRequest).getBody().getData();
    }

    @Override
    public List<ReadCouponFormResponse> getAllCouponForm() {
        return couponFormControllerClient.readAllCouponForms().getBody().getData();
    }

    @Override
    public List<ReadMemberResponse> getMembers() {
        return couponMemberControllerClient.getMembers().getBody().getData();
    }
}
