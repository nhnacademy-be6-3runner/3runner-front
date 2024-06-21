package com.nhnacademy.bookstore.book.bookLike.repository.impl;

import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.book.bookLike.repository.BookLikeCustomRepository;
import com.nhnacademy.bookstore.entity.book.QBook;
import com.nhnacademy.bookstore.entity.bookLike.QBookLike;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * 북-도서 기능을 위한 custom repository 구현체.
 * @author 김은비
 */
public class BookLikeCustomRepositoryImpl implements BookLikeCustomRepository {
  private final JPAQueryFactory jpaQueryFactory;
  private final QBookLike qBookLike = QBookLike.bookLike;
  private final QBook qBook = QBook.book;


  public BookLikeCustomRepositoryImpl(EntityManager entityManager) {
    this.jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public Page<BookListResponse> findBookLikeByMemberId(Long memberId, Pageable pageable) {
    List<BookListResponse> content = jpaQueryFactory.select(Projections.constructor(BookListResponse.class,
        qBook.title,
        qBook.price,
        qBook.sellingPrice,
        qBook.author))
        .from(qBookLike)
        .join(qBookLike.book, qBook)
        .where(qBookLike.member.id.eq(memberId))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
    long totalCount = Optional.ofNullable(jpaQueryFactory.select(qBookLike.count())
        .from(qBookLike)
        .where(qBookLike.member.id.eq(memberId))
        .fetchOne()).orElse(0L);
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
  public Page<BookListResponse> findBooksOrderByLike(Pageable pageable) {
    List<BookListResponse> content = jpaQueryFactory.select(Projections.constructor(BookListResponse.class,
        qBook.title,
        qBook.price,
        qBook.sellingPrice,
        qBook.author))
        .from(qBook)
        .leftJoin(qBookLike).on(qBook.id.eq(qBookLike.book.id))
        .groupBy(qBook.id)
        .orderBy(qBookLike.count().desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
    long totalCount = Optional.ofNullable(jpaQueryFactory.select(qBookLike.count())
        .from(qBookLike)
        .fetchOne())
        .orElse(0L);
    return new PageImpl<>(content, pageable, totalCount);
  }
}
