package com.nhnacademy.bookstore.purchase.purchase.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstore.entity.purchase.Purchase;
import com.nhnacademy.bookstore.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.bookstore.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.bookstore.purchase.purchase.exception.PurchaseDoesNotExistException;
import com.nhnacademy.bookstore.purchase.purchase.exception.PurchaseNoAuthorizationException;
import com.nhnacademy.bookstore.purchase.purchase.repository.PurchaseRepository;
import com.nhnacademy.bookstore.purchase.purchase.service.PurchaseManagerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseManagerServiceImpl implements PurchaseManagerService {
	private final PurchaseRepository purchaseRepository;

	@Override
	public List<ReadPurchaseResponse> readPurchaseAll() {
		List<Purchase> purchases = purchaseRepository.findAll();
		return purchases.stream().map(purchase -> ReadPurchaseResponse.builder()
				.id(purchase.getId())
				.orderNumber(purchase.getOrderNumber())
				.status(purchase.getStatus())
				.deliveryPrice(purchase.getDeliveryPrice()).totalPrice(purchase.getTotalPrice())
				.createdAt(purchase.getCreatedAt())
				.road(purchase.getRoad())
				.password(purchase.getPassword())
				.memberType(purchase.getMemberType())
				.build())
			.toList();
	}

	@Override
	public Long updatePurchaseStatus(String purchaseId, String status) {

		Purchase purchase = purchaseRepository.findPurchaseByOrderNumber(UUID.fromString(purchaseId))
			.orElseThrow(() -> new PurchaseDoesNotExistException(""));


		purchase.setStatus(PurchaseStatus.fromString(status));
		Purchase t = purchaseRepository.save(purchase);

		return t.getId();
	}
}
