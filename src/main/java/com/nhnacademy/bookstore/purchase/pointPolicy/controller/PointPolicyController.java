package com.nhnacademy.bookstore.purchase.pointPolicy.controller;

import com.nhnacademy.bookstore.purchase.coupon.feign.dto.response.ReadCouponFormResponse;
import com.nhnacademy.bookstore.purchase.pointPolicy.dto.PointPolicyResponseRequest;
import com.nhnacademy.bookstore.purchase.pointPolicy.service.PointPolicyService;
import com.nhnacademy.bookstore.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointPolicyController {
    private final PointPolicyService pointPolicyService;

    @PostMapping("/bookstore/points/policies")
    private ApiResponse<Long> saveOrUpdatePolicy(
            @RequestBody PointPolicyResponseRequest pointPolicyResponseRequest) {

        return ApiResponse.success(pointPolicyService.update(pointPolicyResponseRequest.policyKey(), pointPolicyResponseRequest.policyValue()));
    }


    @GetMapping("/bookstore/points/policies")
    private ApiResponse<List<PointPolicyResponseRequest>> readPolicy() {
        return ApiResponse.success(pointPolicyService.readAll());
    }

}
