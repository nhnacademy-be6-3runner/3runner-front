package com.nhnacademy.bookstore.elastic.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstore.elastic.document.book.BookDocument;

public interface ElasticSearchCustomBookRepository {
	// List<BookDocument> searchBooks(String keyword);
	Page<BookDocument> searchProductsByProductName(String keyword, Pageable pageable);
}
