package com.nhnacademy.bookstore.elastic.book.repository;

import org.springframework.data.repository.CrudRepository;

import com.nhnacademy.bookstore.elastic.document.book.BookDocument;

public interface ElasticSearchBookRepository extends
	CrudRepository<BookDocument, Long> {
}
