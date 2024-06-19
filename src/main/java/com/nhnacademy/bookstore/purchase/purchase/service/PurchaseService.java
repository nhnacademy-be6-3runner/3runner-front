package com.nhnacademy.bookstore.purchase.purchase.service;

import com.nhnacademy.bookstore.entity.purchase.Purchase;
import com.nhnacademy.bookstore.purchase.purchase.dto.request.CreatePurchaseRequest;
import com.nhnacademy.bookstore.purchase.purchase.dto.request.UpdatePurchaseRequest;
import com.nhnacademy.bookstore.purchase.purchase.dto.response.ReadPurchaseResponse;

public interface PurchaseService {
    Long createPurchase(CreatePurchaseRequest createPurchaseRequest, Long memberId);
    Long updatePurchase(UpdatePurchaseRequest updatePurchaseRequest, Long purchaseId);
    ReadPurchaseResponse readPurchase(Long purchaseId);
    void deletePurchase(Long purchaseId);
}