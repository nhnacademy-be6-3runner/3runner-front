package com.nhnacademy.bookstore.elastic.book.repository;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.nhnacademy.bookstore.elastic.document.book.BookDocument;

public interface ElasticSearchBookRepository extends ElasticsearchRepository<BookDocument, Long> {

	List<BookDocument> findByTitle(String title);

	@Query("{"
		+ "    \"function_score\": {"
		+ "      \"query\": {"
		+ "        \"multi_match\": {"
		+ "          \"query\": \"불편\", "
		+ "          \"minimum_should_match\": \"70%\","
		+ "          \"fields\": [\"title^100\", \"author^50\", \"publisher\"]"
		+ "        }"
		+ "      }"
		+ "    }"
		+ "  }")
	List<BookDocument> findByCustomQuery(String query);
}
