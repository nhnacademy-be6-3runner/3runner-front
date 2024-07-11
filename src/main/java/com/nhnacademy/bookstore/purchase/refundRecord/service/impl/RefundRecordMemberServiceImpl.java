package com.nhnacademy.bookstore.purchase.refundRecord.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.bookstore.entity.purchaseBook.PurchaseBook;
import com.nhnacademy.bookstore.entity.refund.Refund;
import com.nhnacademy.bookstore.entity.refundRecord.RefundRecord;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadBookByPurchase;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadPurchaseBookResponse;
import com.nhnacademy.bookstore.purchase.purchaseBook.exception.NotExistsPurchaseBook;
import com.nhnacademy.bookstore.purchase.purchaseBook.repository.PurchaseBookCustomRepository;
import com.nhnacademy.bookstore.purchase.purchaseBook.repository.PurchaseBookRepository;
import com.nhnacademy.bookstore.purchase.refund.exception.NotExistsRefund;
import com.nhnacademy.bookstore.purchase.refund.repository.RefundRepository;
import com.nhnacademy.bookstore.purchase.refundRecord.dto.response.ReadRefundRecordResponse;
import com.nhnacademy.bookstore.purchase.refundRecord.exception.AlreadyExistsRefundRecordRedis;
import com.nhnacademy.bookstore.purchase.refundRecord.exception.NotExistsRefundRecordRedis;
import com.nhnacademy.bookstore.purchase.refundRecord.repository.RefundRecordRedisRepository;
import com.nhnacademy.bookstore.purchase.refundRecord.repository.RefundRecordRepository;
import com.nhnacademy.bookstore.purchase.refundRecord.service.RefundRecordMemberService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RefundRecordMemberServiceImpl implements RefundRecordMemberService {

	private final RefundRecordRepository refundRecordRepository;
	private final RefundRecordRedisRepository refundRecordRedisRepository;
	private final PurchaseBookRepository purchaseBookRepository;
	private final RefundRepository refundRepository;
	private final PurchaseBookCustomRepository purchaseBookCustomRepository;

	@Override
	public Long createRefundRecordRedis(Long memberId, Long purchaseBookId, int price, int quantity,
		ReadBookByPurchase readBookByPurchase) {
		if (refundRecordRedisRepository.detailIsHit(memberId.toString(), purchaseBookId)) {
			throw new AlreadyExistsRefundRecordRedis();
		}
		ReadRefundRecordResponse readRefundRecordResponse = ReadRefundRecordResponse.builder()
			.readBookByPurchase(readBookByPurchase)
			.id(purchaseBookId)
			.price(price)
			.quantity(quantity)
			.build();
		return refundRecordRedisRepository.create(memberId.toString(), purchaseBookId, readRefundRecordResponse);
	}

	@Override
	public Long createRefundRecordAllRedis(Long memberId, Long orderNumber) {
		if (refundRecordRedisRepository.isHit(memberId.toString())) {
			throw new AlreadyExistsRefundRecordRedis();
		}
		List<ReadPurchaseBookResponse> responses = purchaseBookCustomRepository.readBookPurchaseResponses(orderNumber);
		for (ReadPurchaseBookResponse readPurchaseBookResponse : responses) {
			createRefundRecordRedis(memberId, readPurchaseBookResponse.id(), readPurchaseBookResponse.price(),
				readPurchaseBookResponse.quantity(), readPurchaseBookResponse.readBookByPurchase());
		}
		return orderNumber;
	}

	@Override
	public Long updateRefundRecordRedis(Long memberId, Long purchaseBookId, int quantity) {
		if (!refundRecordRedisRepository.detailIsHit(memberId.toString(), purchaseBookId)) {
			ReadPurchaseBookResponse purchaseBook = purchaseBookCustomRepository.readPurchaseBookResponse(
				purchaseBookId);
			return createRefundRecordRedis(memberId, purchaseBookId,
				(purchaseBook.price() / purchaseBook.quantity()) * quantity, quantity,
				purchaseBook.readBookByPurchase());

		}
		return refundRecordRedisRepository.update(memberId.toString(), purchaseBookId, quantity);
	}

	@Override
	public Long updateRefundRecordAllRedis(Long memberId, Long orderNumber) {
		if (!refundRecordRedisRepository.isHit(memberId.toString())) {
			return createRefundRecordAllRedis(memberId, orderNumber);
		}
		List<ReadPurchaseBookResponse> responses = purchaseBookCustomRepository.readBookPurchaseResponses(orderNumber);
		for (ReadPurchaseBookResponse readPurchaseBookResponse : responses) {
			if (!refundRecordRedisRepository.detailIsHit(memberId.toString(), readPurchaseBookResponse.id())) {
				createRefundRecordRedis(memberId, readPurchaseBookResponse.id(),
					readPurchaseBookResponse.price(),
					readPurchaseBookResponse.quantity(), readPurchaseBookResponse.readBookByPurchase());
			} else {
				refundRecordRedisRepository.update(memberId.toString(), readPurchaseBookResponse.id(),
					readPurchaseBookResponse.quantity());
			}
		}
		return orderNumber;

	}

	@Override
	public Long updateRefundRecordZeroAllRedis(Long memberId, Long orderNumber) {
		if (!refundRecordRedisRepository.isHit(memberId.toString())) {
			return createRefundRecordAllRedis(memberId, orderNumber);
		}
		List<ReadPurchaseBookResponse> responses = purchaseBookCustomRepository.readBookPurchaseResponses(orderNumber);
		for (ReadPurchaseBookResponse readPurchaseBookResponse : responses) {
			if (!refundRecordRedisRepository.detailIsHit(memberId.toString(), readPurchaseBookResponse.id())) {
				createRefundRecordRedis(memberId, readPurchaseBookResponse.id(),
					0,
					0, readPurchaseBookResponse.readBookByPurchase());
			} else {
				refundRecordRedisRepository.update(memberId.toString(), readPurchaseBookResponse.id(),
					0);
			}
		}
		return orderNumber;
	}

	@Override
	public Long deleteRefundRecordRedis(Long memberId, Long purchaseBookId) {
		if (!refundRecordRedisRepository.isHit(memberId.toString())) {
			throw new NotExistsRefundRecordRedis();
		}
		return refundRecordRedisRepository.delete(memberId.toString(), purchaseBookId);
	}

	@Override
	public List<ReadRefundRecordResponse> readRefundRecordRedis(Long memberId) {
		if (!refundRecordRedisRepository.isHit(memberId.toString())) {
			throw new NotExistsRefundRecordRedis();
		}

		return refundRecordRedisRepository.readAll(memberId.toString());
	}

	@Override
	public Boolean createRefundRecord(Long memberId, Long refundId) {
		if (!refundRecordRedisRepository.isHit(memberId.toString())) {
			return false;
		}
		Refund refund = refundRepository.findById(refundId).orElseThrow(NotExistsRefund::new);

		List<ReadRefundRecordResponse> readRefundRecordResponseList = readRefundRecordRedis(memberId);

		for (ReadRefundRecordResponse readRefundRecordResponse : readRefundRecordResponseList) {
			RefundRecord refundRecord = new RefundRecord();
			refundRecord.setRefund(refund);
			refundRecord.setPurchaseBook(purchaseBookRepository.findById(readRefundRecordResponse.id()).orElseThrow(
				NotExistsPurchaseBook::new));
			refundRecord.setPrice(readRefundRecordResponse.price());
			refundRecord.setQuantity(readRefundRecordResponse.quantity());
			refundRecordRepository.save(refundRecord);
		}

		return true;

	}

}
