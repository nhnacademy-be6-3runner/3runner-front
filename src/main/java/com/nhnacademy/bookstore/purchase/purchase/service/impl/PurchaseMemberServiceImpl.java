package com.nhnacademy.bookstore.purchase.purchase.service.impl;

import com.nhnacademy.bookstore.entity.purchase.Purchase;
import com.nhnacademy.bookstore.entity.purchase.enums.MemberType;
import com.nhnacademy.bookstore.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.bookstore.purchase.purchase.dto.response.CreatePurchaseRequest;
import com.nhnacademy.bookstore.purchase.purchase.dto.response.UpdatePurchaseRequest;
import com.nhnacademy.bookstore.purchase.purchase.repository.PurchaseRepository;
import com.nhnacademy.bookstore.purchase.purchase.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class PurchaseMemberServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;

    @Override
    public void createPurchase(CreatePurchaseRequest createPurchaseRequest, Long memberId) {
        purchaseRepository.save(new Purchase(
                UUID.randomUUID(),
                PurchaseStatus.PROCESSING,
                createPurchaseRequest.deliveryPrice(),
                createPurchaseRequest.totalPrice(),
                ZonedDateTime.now(),
                createPurchaseRequest.road(),
                null,
                MemberType.MEMBER,
                null,
                null,
                null,
                null
        ));
    }

    @Override
    public Purchase updatePurchase(UpdatePurchaseRequest updatePurchaseRequest, Long purchaseId) {
        return null;
    }

    @Override
    public Purchase readPurchase() {
        return null;
    }

    @Override
    public void deletePurchase(Long purchaseId) {

    }
}
