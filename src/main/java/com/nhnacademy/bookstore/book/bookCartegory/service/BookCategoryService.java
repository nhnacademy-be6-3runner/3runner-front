package com.nhnacademy.bookstore.book.bookCartegory.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.book.bookCartegory.dto.request.CreateBookCategoryRequest;
import com.nhnacademy.bookstore.book.bookCartegory.dto.request.UpdateBookCategoryRequest;
import com.nhnacademy.bookstore.book.bookCartegory.dto.response.BookCategoriesChildrenResponse;

public interface BookCategoryService {
	void createBookCategory(CreateBookCategoryRequest dto);

	void updateBookCategory(long bookCategoryId, UpdateBookCategoryRequest dto);

	void deletedBookCategory(Long id);

	List<BookCategoriesChildrenResponse> readBookWithCategoryList(Long bookId);

	Page<BookListResponse> readCategoriesWithBookList(List<Long> categoryList, Pageable pageable);
}
