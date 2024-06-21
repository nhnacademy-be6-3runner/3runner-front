package com.nhnacademy.bookstore.book.bookCartegory.repository;

import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.book.bookCartegory.dto.response.BookCategoriesResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookCategoryCustomRepository {
    Page<BookListResponse> categoryWithBookList(Long categoryId, Pageable pageable);
    List<BookCategoriesResponse> bookWithCategoryList(Long bookId);
    Page<BookListResponse> categoriesWithBookList(List<Long> categoryList, Pageable pageable);
}
