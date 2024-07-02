package com.nhnacademy.bookstore.elastic.book.repository.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import com.nhnacademy.bookstore.elastic.book.repository.ElasticSearchCustomBookRepository;
import com.nhnacademy.bookstore.elastic.document.book.BookDocument;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ElasticSearchCustomBookRepositoryImpl implements ElasticSearchCustomBookRepository {

	private final ElasticsearchOperations elasticsearchOperations;

	// @Override
	// public List<BookDocument> searchBooks(String keyword) {
	// 	BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
	// 		.should(QueryBuilders.matchQuery("title", keyword).boost(2))
	// 		.should(QueryBuilders.matchQuery("author", keyword).boost(1))
	// 		.should(QueryBuilders.matchQuery("publisher", keyword).boost(1))
	// 		.should(QueryBuilders.matchQuery("categoryList", keyword).boost(199));
	//
	// 	NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
	// 		.withQuery(boolQuery)
	// 		.build();
	//
	// 	SearchHits<BookDocument> searchHits = elasticsearchOperations.search(searchQuery, BookDocument.class);
	// 	return searchHits.getSearchHits().stream()
	// 		.map(hit -> hit.getContent())
	// 		.collect(Collectors.toList());
	// }
	// Query query = new CriteriaQuery(criteria).setPageable(pageable);

	@Override
	public SearchHits<BookDocument> searchProductsByProductName(String keyword, Pageable pageable) {
		Query searchQuery = new CriteriaQuery(new Criteria()
			.subCriteria(new Criteria("title").boost(100).matches(keyword))
			.subCriteria(new Criteria("author").boost(50).matches(keyword))
			.subCriteria(new Criteria("publisher").matches(keyword))
			// .endsWith("70%")
		);

		return elasticsearchOperations.search(searchQuery, BookDocument.class);

	}

}
