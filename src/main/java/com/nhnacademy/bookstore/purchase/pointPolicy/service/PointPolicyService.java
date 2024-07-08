package com.nhnacademy.bookstore.purchase.pointPolicy.service;

import com.nhnacademy.bookstore.entity.pointPolicy.PointPolicy;
import com.nhnacademy.bookstore.purchase.pointPolicy.dto.PointPolicyResponseRequest;

import java.util.List;
import java.util.Optional;

public interface PointPolicyService {
    Long save(String key, Integer value);
    Long update(String key, Integer value);
    List<PointPolicyResponseRequest> readAll();
    PointPolicyResponseRequest read(String key);
}
