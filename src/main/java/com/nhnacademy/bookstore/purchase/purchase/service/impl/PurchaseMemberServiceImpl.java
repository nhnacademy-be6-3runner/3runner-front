package com.nhnacademy.bookstore.purchase.purchase.service.impl;

import com.nhnacademy.bookstore.entity.purchase.Purchase;
import com.nhnacademy.bookstore.entity.purchase.enums.MemberType;
import com.nhnacademy.bookstore.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.bookstore.member.member.service.MemberService;
import com.nhnacademy.bookstore.purchase.purchase.dto.request.CreatePurchaseRequest;
import com.nhnacademy.bookstore.purchase.purchase.dto.request.UpdatePurchaseRequest;
import com.nhnacademy.bookstore.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.bookstore.purchase.purchase.exception.PurchaseDoesNotExistException;
import com.nhnacademy.bookstore.purchase.purchase.repository.PurchaseRepository;
import com.nhnacademy.bookstore.purchase.purchase.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseMemberServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final MemberService memberService;
    private final EnableSpringDataWebSupport.SpringDataWebSettingsRegistar springDataWebSettingsRegistar;

    @Override
    public Long createPurchase(CreatePurchaseRequest createPurchaseRequest, Long memberId) {
        Purchase purchase = new Purchase(
                UUID.randomUUID(),
                PurchaseStatus.PROCESSING,
                createPurchaseRequest.deliveryPrice(),
                createPurchaseRequest.totalPrice(),
                ZonedDateTime.now(),
                createPurchaseRequest.road(),
                null,
                MemberType.MEMBER,
                memberService.findById(memberId),
                null,
                null,
                null
        );
        purchaseRepository.save(purchase);
        return purchase.getId();
    }

    @Override
    public Long updatePurchase(UpdatePurchaseRequest updatePurchaseRequest, Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(()-> new PurchaseDoesNotExistException(""));
        purchase.setStatus(updatePurchaseRequest.purchaseStatus());

        purchaseRepository.save(purchase);

        return purchase.getId();
    }

    @Override
    public ReadPurchaseResponse readPurchase(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(()-> new PurchaseDoesNotExistException(""));

        return ReadPurchaseResponse.builder()
                .id(purchase.getId())
                .status(purchase.getStatus())
                .deliveryPrice(purchase.getDeliveryPrice())
                .totalPrice(purchase.getTotalPrice())
                .createdAt(purchase.getCreatedAt())
                .road(purchase.getRoad())
                .password(purchase.getPassword())
                .memberType(purchase.getMemberType())
                .build();
    }

    @Override
    public void deletePurchase(Long purchaseId) {
        Optional<Purchase> purchase = purchaseRepository.findById(purchaseId);
        purchase.ifPresent(purchaseRepository::delete);
    }
}
