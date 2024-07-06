package com.nhnacademy.bookstore.purchase.pointPolicy.service;

import com.nhnacademy.bookstore.purchase.pointPolicy.dto.PointPolicyResponseRequest;

import java.util.List;

public interface PointPolicyService {
    Long save(String key, Integer value);
    Long update(String key, Integer value);
    List<PointPolicyResponseRequest> readAll();
}
