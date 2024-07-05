package com.nhnacademy.bookstore.purchase.refund.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.bookstore.entity.payment.Payment;
import com.nhnacademy.bookstore.entity.purchase.Purchase;
import com.nhnacademy.bookstore.purchase.payment.repository.PaymentRepository;
import com.nhnacademy.bookstore.purchase.purchase.repository.PurchaseRepository;
import com.nhnacademy.bookstore.purchase.refund.repository.RefundRepository;
import com.nhnacademy.bookstore.purchase.refund.service.refundService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class refundServiceImpl implements refundService {

	private final RefundRepository refundRepository;
	private final PurchaseRepository purchaseRepository;
	private final PaymentRepository paymentRepository;

	@Override
	public String readTossOrderId(String orderId) {
		Purchase purchase = purchaseRepository.findPurchaseByOrderNumber(UUID.fromString(orderId)).orElseThrow(()->new RuntimeException("Purchase not found"));
		Payment payment = paymentRepository.findByPurchase(purchase);
		return payment.getTossOrderId();
	}
}
