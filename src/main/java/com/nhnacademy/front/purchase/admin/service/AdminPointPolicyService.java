package com.nhnacademy.front.purchase.admin.service;

import com.nhnacademy.front.purchase.admin.dto.PointPolicyResponseRequest;

import java.util.List;

public interface AdminPointPolicyService {
    Long update(PointPolicyResponseRequest pointPolicyResponseRequest);
    List<PointPolicyResponseRequest> readAll();
    PointPolicyResponseRequest read(String policyKey);
}
