package com.nhnacademy.front.purchase.admin.service.impl;

import com.nhnacademy.front.purchase.admin.dto.PointPolicyResponseRequest;
import com.nhnacademy.front.purchase.admin.feign.PointPolicyControllerClient;
import com.nhnacademy.front.purchase.admin.service.AdminPointPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminPointPolicyServiceImpl implements AdminPointPolicyService {
    private final PointPolicyControllerClient controllerClient;
    @Override
    public Long update(PointPolicyResponseRequest pointPolicyResponseRequest) {
        return controllerClient.saveOrUpdatePolicy(pointPolicyResponseRequest).getBody().getData();
    }

    @Override
    public List<PointPolicyResponseRequest> readAll() {
        return controllerClient.readPolicy().getBody().getData();
    }

    @Override
    public PointPolicyResponseRequest read(String policyKey) {
        return controllerClient.readOne(policyKey).getBody().getData();
    }
}
