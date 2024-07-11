package com.nhnacademy.bookstore.purchase.refund.service.impl;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.entity.payment.Payment;
import com.nhnacademy.bookstore.entity.purchase.Purchase;
import com.nhnacademy.bookstore.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.bookstore.entity.refund.Refund;
import com.nhnacademy.bookstore.entity.refund.enums.RefundStatus;
import com.nhnacademy.bookstore.entity.refundRecord.RefundRecord;
import com.nhnacademy.bookstore.member.member.service.MemberPointService;
import com.nhnacademy.bookstore.purchase.payment.repository.PaymentRepository;
import com.nhnacademy.bookstore.purchase.purchase.repository.PurchaseRepository;
import com.nhnacademy.bookstore.purchase.purchaseBook.exception.NotExistsPurchase;
import com.nhnacademy.bookstore.purchase.refund.dto.response.ReadRefundResponse;
import com.nhnacademy.bookstore.purchase.refund.exception.ImpossibleAccessRefundException;
import com.nhnacademy.bookstore.purchase.refund.exception.NotExistsRefundRecord;
import com.nhnacademy.bookstore.purchase.refund.repository.RefundCustomRepository;
import com.nhnacademy.bookstore.purchase.refund.repository.impl.RefundCustomRepositoryImpl;
import com.nhnacademy.bookstore.purchase.refundRecord.repository.RefundRecordRepository;
import com.nhnacademy.bookstore.purchase.refund.repository.RefundRepository;
import com.nhnacademy.bookstore.purchase.refund.service.RefundService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

	private final RefundRepository refundRepository;
	private final PurchaseRepository purchaseRepository;
	private final PaymentRepository paymentRepository;
	private final MemberPointService memberPointSerivce;
	private final RefundRecordRepository refundRecordRepository;
	private final RefundCustomRepository refundCustomRepository;

	@Override
	public String readTossOrderId(String orderId) {
		Purchase purchase = purchaseRepository.findPurchaseByOrderNumber(UUID.fromString(orderId))
			.orElseThrow(() -> new RuntimeException("Purchase not found"));
		Payment payment = paymentRepository.findByPurchase(purchase);
		return payment.getTossOrderId();
	}

	@Override
	public Long createRefund(Long orderId, String refundContent, Integer price, Long memberId) {

		Purchase purchase = purchaseRepository.findById(orderId).orElseThrow(NotExistsPurchase::new);

		if (!Objects.equals(memberId, purchase.getMember().getId())) {
			throw new ImpossibleAccessRefundException();
		}

		Refund refund = new Refund();
		refund.setRefundContent(refundContent);
		refund.setPrice(price);
		refund.setRefundStatus(RefundStatus.READY);
		purchase.setStatus(PurchaseStatus.REFUNDED_REQUEST);

		purchaseRepository.save(purchase);
		refundRepository.save(refund);

		return refund.getId();
	}

	@Override
	public Boolean updateSuccessRefund(Long refundId) {

		Refund refund = refundRepository.findById(refundId).orElse(null);
		if (Objects.isNull(refund)) {
			return false;
		}
		if(refund.getRefundStatus().equals(RefundStatus.SUCCESS)) {
			return false;
		}
		Purchase purchase = refund.getRefundRecordList().getFirst().getPurchaseBook().getPurchase();

		Member member = purchase.getMember();

		memberPointSerivce.updatePoint(member.getId(), (long)refund.getPrice());

		refund.setRefundStatus(RefundStatus.SUCCESS);

		if (purchase.getTotalPrice() == refund.getPrice()) {
			purchase.setStatus(PurchaseStatus.REFUNDED_COMPLETED);
		} else {
			purchase.setStatus(PurchaseStatus.CONFIRMATION);
		}


		refundRepository.save(refund);
		purchaseRepository.save(purchase);

		return true;
	}

	@Override
	public Boolean updateRefundRejected(Long refundId) {
		Refund refund = refundRepository.findById(refundId).orElse(null);
		if (Objects.isNull(refund)) {
			return false;
		}
		Purchase purchase = refund.getRefundRecordList().getFirst().getPurchaseBook().getPurchase();

		refund.setRefundStatus(RefundStatus.FAILED);
		ZonedDateTime tenDaysAgo = ZonedDateTime.now().minusDays(10);
		if (purchase.getShippingDate().isBefore(tenDaysAgo)) {
			purchase.setStatus(PurchaseStatus.CONFIRMATION);
		} else {
			purchase.setStatus(PurchaseStatus.DELIVERY_COMPLETED);
		}
		return true;
	}

	@Override
	public ReadRefundResponse readRefund(Long refundId) {
		return refundCustomRepository.readRefund(refundId);
	}

	@Override
	public Long createRefundCancelPayment(String orderId) {
		Purchase purchase = purchaseRepository.findPurchaseByOrderNumber(UUID.fromString(orderId))
			.orElseThrow(NotExistsPurchase::new);

		Refund refund = new Refund();
		refund.setRefundContent("결제 취소");
		refund.setPrice(purchase.getTotalPrice());
		refund.setRefundStatus(RefundStatus.SUCCESS);
		purchase.setStatus(PurchaseStatus.REFUNDED_COMPLETED);

		purchaseRepository.save(purchase);
		refundRepository.save(refund);

		return refund.getId();
	}
}
