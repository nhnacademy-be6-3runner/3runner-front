package com.nhnacademy.bookstore.book.bookCartegory.repository;

import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.book.bookCartegory.dto.response.BookCategoriesResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookCategoryCustomRepository {
    // TODO 해당 카테고리에 해당하는 도서 목록 조회
    Page<BookListResponse> categoryWithBookList(Long categoryId, Pageable pageable);

    // TODO 해당 도서에 해당하는 카테고리 조회
    List<BookCategoriesResponse> bookWithCategoryList(Long bookId);

    // TODO 다중 선택 카테고리
    Page<BookListResponse> categoriesWithBookList(List<Long> categoryList, Pageable pageable);
}
