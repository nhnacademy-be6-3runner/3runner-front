package com.nhnacademy.bookstore.elastic.book.repository;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;

import com.nhnacademy.bookstore.elastic.document.book.BookDocument;

public interface ElasticSearchCustomBookRepository {
	// List<BookDocument> searchBooks(String keyword);

	SearchHits<BookDocument> searchProductsByProductName(String keyword, Pageable pageable);
}
