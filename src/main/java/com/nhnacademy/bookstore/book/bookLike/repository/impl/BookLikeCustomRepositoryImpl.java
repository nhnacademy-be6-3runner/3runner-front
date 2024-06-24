package com.nhnacademy.bookstore.book.bookLike.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.book.bookLike.repository.BookLikeCustomRepository;
import com.nhnacademy.bookstore.entity.book.QBook;
import com.nhnacademy.bookstore.entity.bookImage.QBookImage;
import com.nhnacademy.bookstore.entity.bookImage.enums.BookImageType;
import com.nhnacademy.bookstore.entity.bookLike.QBookLike;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

/**
 * 북-도서 기능을 위한 custom repository 구현체.
 *
 * @author 김은비
 */
public class BookLikeCustomRepositoryImpl implements BookLikeCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;
	private final QBookLike qBookLike = QBookLike.bookLike;
	private final QBook qBook = QBook.book;
	private final QBookImage qBookImage = QBookImage.bookImage;

	public BookLikeCustomRepositoryImpl(EntityManager entityManager) {
		this.jpaQueryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Page<BookListResponse> findBookLikeByMemberId(long memberId, Pageable pageable) {
		List<BookListResponse> content = jpaQueryFactory.select(
				Projections.constructor(BookListResponse.class,
					qBook.title,
					qBook.price,
					qBook.sellingPrice,
					qBook.author,
					qBookImage.url))
			.from(qBookLike)
			.join(qBookLike.book, qBook)
			.leftJoin(qBookImage)
			.on(qBookImage.book.id.eq(qBook.id).and(qBookImage.type.eq(BookImageType.MAIN)))
			.where(qBookLike.member.id.eq(memberId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
		Long totalCount = jpaQueryFactory.select(qBookLike.count())
			.from(qBookLike)
			.where(qBookLike.member.id.eq(memberId))
			.fetchOne();
		totalCount = totalCount != null ? totalCount : 0L;
		return new PageImpl<>(content, pageable, totalCount);
	}

	@Override
	public long countLikeByBookId(long bookId) {
		return Optional.ofNullable(jpaQueryFactory.select(qBookLike.count())
				.from(qBookLike)
				.where(qBookLike.book.id.eq(bookId))
				.fetchOne())
			.orElse(0L);
	}

	@Override
	public Page<BookListResponse> findBooksOrderByLikes(Pageable pageable) {
		List<BookListResponse> content = jpaQueryFactory.select(
				Projections.constructor(BookListResponse.class,
					qBook.title,
					qBook.price,
					qBook.sellingPrice,
					qBook.author,
					qBookImage.url))
			.from(qBook)
			.leftJoin(qBookLike).on(qBook.id.eq(qBookLike.book.id))
			.leftJoin(qBookImage)
			.on(qBookImage.book.id.eq(qBook.id).and(qBookImage.type.eq(BookImageType.MAIN)))
			.groupBy(qBook.id)
			.orderBy(qBookLike.count().desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
		Long totalCount = jpaQueryFactory.select(qBookLike.count())
			.from(qBookLike)
			.fetchOne();
		totalCount = totalCount != null ? totalCount : 0L;
		return new PageImpl<>(content, pageable, totalCount);
	}
}
