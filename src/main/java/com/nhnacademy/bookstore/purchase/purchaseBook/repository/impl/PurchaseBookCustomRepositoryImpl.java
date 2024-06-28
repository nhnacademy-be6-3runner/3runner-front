package com.nhnacademy.bookstore.purchase.purchaseBook.repository.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.nhnacademy.bookstore.entity.book.QBook;
import com.nhnacademy.bookstore.entity.purchase.QPurchase;
import com.nhnacademy.bookstore.entity.purchaseBook.QPurchaseBook;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadBookByPurchase;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadPurchaseBookResponse;
import com.nhnacademy.bookstore.purchase.purchaseBook.repository.PurchaseBookCustomRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Repository
public class PurchaseBookCustomRepositoryImpl implements PurchaseBookCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;
	private final QPurchaseBook qPurchaseBook = QPurchaseBook.purchaseBook;
	private final QBook qBook = QBook.book;
	private final QPurchase qPurchase = QPurchase.purchase;

	public PurchaseBookCustomRepositoryImpl(EntityManager entityManager) {
		this.jpaQueryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Page<ReadPurchaseBookResponse> readBookPurchaseResponses(Long purchaseId, Pageable pageable) {
		List<ReadPurchaseBookResponse> response = jpaQueryFactory.select(
				Projections.constructor(ReadPurchaseBookResponse.class,
					Projections.constructor(ReadBookByPurchase.class,
						qPurchaseBook.book.title,
						qPurchaseBook.book.price,
						qPurchaseBook.book.author,
						qPurchaseBook.book.sellingPrice,
						qPurchaseBook.book.packing,
						qPurchaseBook.book.publisher,
						qPurchaseBook.book.bookImageList.get(0).url
					),
					qPurchaseBook.id,
					qPurchaseBook.quantity,
					qPurchaseBook.price
				))
			.from(qPurchaseBook)
			.leftJoin(qPurchaseBook.book, qBook)
			.join(qPurchaseBook.purchase, qPurchase)
			.where(qPurchaseBook.purchase.id.eq(purchaseId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = (jpaQueryFactory.select(qPurchaseBook.count())
			.from(qPurchaseBook)
			.where(qPurchaseBook.purchase.id.eq(purchaseId))
			.fetchOne());
		if (total <= 0) {
			throw new NullPointerException();
		}

		return new PageImpl<>(response, pageable, total);
	}
}
