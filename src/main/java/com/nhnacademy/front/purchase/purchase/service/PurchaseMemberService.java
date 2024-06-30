package com.nhnacademy.front.purchase.purchase.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchase;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseResponse;

public interface PurchaseMemberService {

	Page<ReadPurchase> readPurchases(Long userId);
}
