package com.nhnacademy.bookstore.book.book.repository.impl;

import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.book.book.repository.BookCustomRepository;
import com.nhnacademy.bookstore.entity.book.QBook;
import com.nhnacademy.bookstore.entity.bookImage.QBookImage;
import com.nhnacademy.bookstore.entity.bookImage.enums.BookImageType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private QBook qBook = QBook.book;
    private QBookImage qBookImage = QBookImage.bookImage;

    @Override
    public Page<BookListResponse> readBookList(Pageable pageable) {
        List<BookListResponse> content = jpaQueryFactory.select(
                        Projections.constructor(BookListResponse.class,
                                qBook.id,
                                qBook.title,
                                qBook.price,
                                qBook.sellingPrice,
                                qBook.author,
                                qBookImage.url))
                .from(qBook)
                .leftJoin(qBookImage)
                .on(qBookImage.book.id.eq(qBook.id).and(qBookImage.type.eq(BookImageType.MAIN)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return null;
    }
}
