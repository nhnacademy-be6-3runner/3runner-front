package com.nhnacademy.bookstore.book.bookCartegory.service;

import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.book.bookCartegory.dto.request.CreateBookCategoryRequest;
import com.nhnacademy.bookstore.book.bookCartegory.dto.request.UpdateBookCategoryRequest;
import com.nhnacademy.bookstore.book.bookCartegory.dto.response.BookCategoriesResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookCategoryService {
    void createBookCategory(CreateBookCategoryRequest dto);
    void updateBookCategory(long bookCategoryId, UpdateBookCategoryRequest dto);
    void deletedBookCategory(Long id);

    List<BookCategoriesResponse> readBookWithCategoryList(Long bookId);

    Page<BookListResponse> readCategoriesWithBookList(List<Long> categoryList, Pageable pageable);
}
