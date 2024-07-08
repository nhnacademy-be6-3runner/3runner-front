package com.nhnacademy.front.refund.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;

public interface RefundService {

	List<ReadPurchaseBookResponse> readGuestPurchaseBooks(String orderNumber);

	Map<String, Object> refundToss(String orderNumber,String cancelReason);

}
