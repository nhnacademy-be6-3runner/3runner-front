package com.nhnacademy.bookstore.purchase.pointPolicy.service.impl;

import com.nhnacademy.bookstore.entity.pointPolicy.PointPolicy;
import com.nhnacademy.bookstore.purchase.pointPolicy.dto.PointPolicyResponseRequest;
import com.nhnacademy.bookstore.purchase.pointPolicy.repository.PointPolicyRepository;
import com.nhnacademy.bookstore.purchase.pointPolicy.service.PointPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PointPolicyServiceImpl implements PointPolicyService {
    private final PointPolicyRepository pointPolicyRepository;

    @Override
    public Long save(String policyName, Integer policyValue) {
        PointPolicy pointPolicy = new PointPolicy(policyName, policyValue);
        pointPolicyRepository.save(pointPolicy);
        return pointPolicy.getId();
    }

    @Override
    public Long update(String policyName, Integer policyValue) {
        PointPolicy pointPolicy = pointPolicyRepository.findByPolicyName(policyName).orElseGet(() -> saveAndReturnPolicy(policyName, policyValue));
        pointPolicy.setPolicyValue(policyValue);
        pointPolicyRepository.save(pointPolicy);
        return pointPolicy.getId();
    }

    @Override
    public List<PointPolicyResponseRequest> readAll() {
        return pointPolicyRepository
                .findAll()
                .stream()
                .map(o->PointPolicyResponseRequest.builder()
                        .policyKey(o.getPolicyName()).policyValue(o.getPolicyValue()).build())
                .toList();
    }

    private PointPolicy saveAndReturnPolicy(String key, Integer value) {
        Long id = save(key, value);
        return pointPolicyRepository.findById(id).orElseThrow(() -> new IllegalStateException("저장 이후에도 찾지 못했습니다"));
    }
}
