package com.nhnacademy.bookstore.book.bookCartegory.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.entity.category.Category;

public interface BookCategoryCustomRepository {
	Page<BookListResponse> categoryWithBookList(Long categoryId, Pageable pageable);

	List<Category> bookWithCategoryList(Long bookId);

	Page<BookListResponse> categoriesWithBookList(List<Long> categoryList, Pageable pageable);
}
