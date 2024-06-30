package com.nhnacademy.front.purchase.purchase.service;

import org.springframework.data.domain.Page;

import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchase;

public interface PurchaseDetailMemberService {

	Page<ReadPurchase> readPurchases(Long userId);
}
