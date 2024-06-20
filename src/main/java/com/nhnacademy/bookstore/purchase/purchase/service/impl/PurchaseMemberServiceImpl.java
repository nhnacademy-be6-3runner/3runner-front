package com.nhnacademy.bookstore.purchase.purchase.service.impl;

import com.nhnacademy.bookstore.entity.purchase.Purchase;
import com.nhnacademy.bookstore.entity.purchase.enums.MemberType;
import com.nhnacademy.bookstore.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.bookstore.member.member.service.MemberService;
import com.nhnacademy.bookstore.purchase.purchase.dto.request.CreatePurchaseRequest;
import com.nhnacademy.bookstore.purchase.purchase.dto.request.UpdatePurchaseRequest;
import com.nhnacademy.bookstore.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.bookstore.purchase.purchase.exception.PurchaseAlreadyExistException;
import com.nhnacademy.bookstore.purchase.purchase.exception.PurchaseDoesNotExistException;
import com.nhnacademy.bookstore.purchase.purchase.exception.PurchaseNoAuthorizationException;
import com.nhnacademy.bookstore.purchase.purchase.repository.PurchaseRepository;
import com.nhnacademy.bookstore.purchase.purchase.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseMemberServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final MemberService memberService;

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
                null, //TODO : Point 구현 후 연결 필요
                null,
                null
        );

        if(purchaseRepository.existsPurchaseByOrderNumber(purchase.getOrderNumber())) {
            throw new PurchaseAlreadyExistException("주문 번호가 중복되었습니다.");
        }

        purchaseRepository.save(purchase);
        return purchase.getId();
    }

    @Override
    public Long updatePurchase(UpdatePurchaseRequest updatePurchaseRequest,Long memberId, Long purchaseId) {
        List<Purchase> purchaseList = purchaseRepository.findPurchasesByMember(memberService.findById(MemberId));
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(()-> new PurchaseDoesNotExistException(""));

        if (!purchaseList.contains(purchase)) {
            throw new PurchaseNoAuthorizationException("권한이 없습니다");
        }

        purchase.setStatus(updatePurchaseRequest.purchaseStatus());

        purchaseRepository.save(purchase);

        return purchase.getId();
    }


    @Override
    public ReadPurchaseResponse readPurchase(Long MemberId, Long purchaseId) {
        List<Purchase> purchaseList = purchaseRepository.findPurchasesByMember(memberService.findById(MemberId));
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(()-> new PurchaseDoesNotExistException(""));

        if (!purchaseList.contains(purchase)) {
            throw new PurchaseNoAuthorizationException("권한이 없습니다");
        }

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
    public void deletePurchase(Long MemberId, Long purchaseId) {
        List<Purchase> purchaseList = purchaseRepository.findPurchasesByMember(memberService.findById(MemberId));
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(()-> new PurchaseDoesNotExistException(""));

        if (!purchaseList.contains(purchase)) {
            throw new PurchaseNoAuthorizationException("권한이 없습니다");
        }

        purchaseRepository.delete(purchase);
    }
}
