package com.nhnacademy.bookstore.purchase.purchase.service;

import com.nhnacademy.bookstore.entity.purchase.Purchase;
import com.nhnacademy.bookstore.purchase.purchase.dto.response.CreatePurchaseRequest;
import com.nhnacademy.bookstore.purchase.purchase.dto.response.UpdatePurchaseRequest;

public interface PurchaseService {
    void createPurchase(CreatePurchaseRequest createPurchaseRequest, Long memberId);
    Purchase updatePurchase(UpdatePurchaseRequest updatePurchaseRequest, Long purchaseId);
    Purchase readPurchase();
    void deletePurchase(Long purchaseId);
}
