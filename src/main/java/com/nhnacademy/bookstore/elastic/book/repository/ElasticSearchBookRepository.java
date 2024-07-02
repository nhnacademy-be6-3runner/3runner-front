package com.nhnacademy.bookstore.elastic.book.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.nhnacademy.bookstore.elastic.document.book.BookDocument;

public interface ElasticSearchBookRepository extends ElasticsearchRepository<BookDocument, Long> {

	List<BookDocument> findByTitle(String title);
}
