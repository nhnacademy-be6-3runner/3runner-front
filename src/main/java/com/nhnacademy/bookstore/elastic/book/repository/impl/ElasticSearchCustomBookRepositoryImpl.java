package com.nhnacademy.bookstore.elastic.book.repository.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
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

	@Override
	public Page<BookDocument> searchProductsByProductName(String keyword, Pageable pageable) {
		Criteria criteria = Criteria.where("title").contains(keyword);

		Query query = new CriteriaQuery(criteria).setPageable(pageable);

		SearchHits<BookDocument> search = elasticsearchOperations.search(query, BookDocument.class);

		List<BookDocument> list = search.stream().map(SearchHit::getContent).collect(Collectors.toList());

		return new PageImpl<>(list, pageable, search.getTotalHits());
	}

}
