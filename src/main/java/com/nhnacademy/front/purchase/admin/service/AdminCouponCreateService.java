package com.nhnacademy.front.purchase.admin.service;

import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateCouponFormRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponFormResponse;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponTypeResponse;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponUsageResponse;
import com.nhnacademy.front.purchase.purchase.dto.member.response.ReadMemberResponse;
import com.nhnacademy.front.util.ApiResponse;

import java.util.List;

public interface AdminCouponCreateService {
    List<ReadCouponUsageResponse> getUsages();
    List<ReadCouponTypeResponse> getTypes();
    Long createCouponForm(CreateCouponFormRequest createCouponFormRequest, Long targetMemberId);
    List<ReadCouponFormResponse> getAllCouponForm();
    List<ReadMemberResponse> getMembers();
}
