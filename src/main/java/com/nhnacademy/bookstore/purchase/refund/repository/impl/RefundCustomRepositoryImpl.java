package com.nhnacademy.bookstore.purchase.refund.repository.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhnacademy.bookstore.entity.purchase.QPurchase;
import com.nhnacademy.bookstore.entity.purchaseBook.QPurchaseBook;
import com.nhnacademy.bookstore.entity.refund.QRefund;
import com.nhnacademy.bookstore.entity.refundRecord.QRefundRecord;
import com.nhnacademy.bookstore.purchase.refund.dto.response.ReadRefundResponse;
import com.nhnacademy.bookstore.purchase.refund.repository.RefundCustomRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Repository
public class RefundCustomRepositoryImpl implements RefundCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;
	private final QRefund qRefund = QRefund.refund;
	private final QRefundRecord qRefundRecord = QRefundRecord.refundRecord;
	private final QPurchaseBook qPurchaseBook = QPurchaseBook.purchaseBook;
	private final QPurchase qPurchase = QPurchase.purchase;

	public RefundCustomRepositoryImpl(EntityManager entityManager) {
		this.jpaQueryFactory = new JPAQueryFactory(entityManager);
	}


	@Override
	public ReadRefundResponse readRefund(Long refundId) {
		return jpaQueryFactory.select(
				Projections.constructor(ReadRefundResponse.class,
					qRefund.refundContent,
					qRefund.price,
					qRefund.id,
					qPurchase.orderNumber
				))
			.from(qRefund)
			.leftJoin(qRefundRecord)
			.on(qRefundRecord.refund.eq(qRefund))
			.join(qRefundRecord.purchaseBook, qPurchaseBook)
			.join(qPurchaseBook.purchase, qPurchase)
			.where(qRefund.id.eq(refundId))
			.groupBy(qRefund.id, qRefund.refundContent, qRefund.price, qPurchase.orderNumber)
			.fetchOne();
	}
}
