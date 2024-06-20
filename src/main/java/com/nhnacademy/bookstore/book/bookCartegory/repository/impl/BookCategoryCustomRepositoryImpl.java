package com.nhnacademy.bookstore.book.bookCartegory.repository.impl;

import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.book.bookCartegory.dto.response.BookCategoriesResponse;
import com.nhnacademy.bookstore.book.bookCartegory.repository.BookCategoryCustomRepository;
import com.nhnacademy.bookstore.entity.bookCategory.QBookCategory;
import com.nhnacademy.bookstore.entity.category.QCategory;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * book-category query dsl 인터페이스 구현체
 * @author 김은비
 */
@Slf4j
@Repository
public class BookCategoryCustomRepositoryImpl implements BookCategoryCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QBookCategory qBookCategory = QBookCategory.bookCategory;

    public BookCategoryCustomRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * @param categoryId 조회할 카테고리 아이디
     * @param pageable 페이지
     * @return 조회된 도서 list
     */
    @Override
    public Page<BookListResponse> categoryWithBookList(Long categoryId, Pageable pageable) {
        List<BookListResponse> content = jpaQueryFactory
                // bookListResponse 객체 생성
                .select(Projections.constructor(BookListResponse.class,
                        qBookCategory.book.id,
                        qBookCategory.book.title,
                        qBookCategory.book.author,
                        qBookCategory.book.price))
                .from(qBookCategory)
                // 두 값이 같은지 확인
                .where(qBookCategory.category.id.eq(categoryId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long totalCount = jpaQueryFactory
                .select(QBookCategory.bookCategory.count())
                .from(qBookCategory)
                .where(qBookCategory.category.id.eq(categoryId))
                // .fetchCount() -> 중단될 예정, 불필요한 쿼리도 불러와서 데이터베이스 성능 저하
                .stream().count();
        return new PageImpl<>(content, pageable, totalCount);
    }


    @Override
    public List<BookCategoriesResponse> bookWithCategoryList(Long bookId) {
        return jpaQueryFactory
                .select(Projections.constructor(BookCategoriesResponse.class,
                        qBookCategory.category.id,
                        qBookCategory.category.name))
                .from(qBookCategory)
                .where(qBookCategory.book.id.eq(bookId))
                .fetch();
    }


    /**
     * @param categoryList 조회할 카테고리 아이디 리스트
     * @param pageable 페이지
     * @return 조회된 도서 list
     */
    @Override
    public Page<BookListResponse> categoriesWithBookList(List<Long> categoryList, Pageable pageable) {
        List<BookListResponse> content = jpaQueryFactory
                .select(Projections.constructor(BookListResponse.class,
                        qBookCategory.book.id,
                        qBookCategory.book.title,
                        qBookCategory.book.author,
                        qBookCategory.book.price))
                .from(qBookCategory)
                .where(qBookCategory.category.id.in(categoryList))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = jpaQueryFactory
                .select(QBookCategory.bookCategory.count())
                .from(qBookCategory)
                .where(qBookCategory.category.id.in(categoryList))
                .stream().count();

        return new PageImpl<>(content, pageable, totalCount);
    }
}
