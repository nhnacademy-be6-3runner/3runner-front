package com.nhnacademy.bookstore.elastic.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.bookstore.book.book.dto.response.ReadBookResponse;
import com.nhnacademy.bookstore.book.book.service.BookService;
import com.nhnacademy.bookstore.book.bookCartegory.service.BookCategoryService;
import com.nhnacademy.bookstore.book.bookTag.dto.request.ReadBookIdRequest;
import com.nhnacademy.bookstore.book.bookTag.dto.response.ReadTagByBookResponse;
import com.nhnacademy.bookstore.book.bookTag.service.BookTagService;
import com.nhnacademy.bookstore.elastic.book.repository.ElasticSearchBookRepository;
import com.nhnacademy.bookstore.elastic.book.repository.ElasticSearchCustomBookRepository;
import com.nhnacademy.bookstore.elastic.document.book.BookDocument;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ElasticTestController {

	@Autowired
	ElasticSearchBookRepository elasticSearchBookRepository;

	@Autowired
	ElasticSearchCustomBookRepository elasticSearchCustomBookRepository;

	@Autowired
	BookService bookService;
	@Autowired
	BookCategoryService bookCategoryService;

	@Autowired
	BookTagService bookTagService;

	@GetMapping("/test/push")
	String push() {

		for (long i = 1; i <= 713; i++) {
			try {
				ReadBookResponse book = bookService.readBookById(i);

				List<String> categoryList = bookCategoryService.readBookCategoryNames(
					i);
				List<ReadTagByBookResponse> readTagList =
					bookTagService.readTagByBookId(ReadBookIdRequest.builder().bookId(i).build());

				List<String> tagList = new ArrayList<>();
				for (ReadTagByBookResponse tag : readTagList) {
					tagList.add(tag.name());
				}

				BookDocument bookDocument = new BookDocument(
					book.id(),
					book.title(),
					book.author(),
					book.imagePath(),
					book.publisher(),
					book.publishedDate().toString(),
					tagList,
					categoryList
				);

				elasticSearchBookRepository.save(bookDocument);
			} catch (Exception e) {
				log.info("{}, 번째", i);
			}
		}

		return "good";
	}

	@GetMapping("/test/search/{indexId}")
	public BookDocument searchIndex(@PathVariable Long indexId) {
		return elasticSearchBookRepository.findById(indexId).orElse(null);
	}

	@GetMapping("/test/search/title/{title}")
	public List<BookDocument> searchTitle(@PathVariable String title) {
		return elasticSearchBookRepository.findByTitle(title);
	}

	@GetMapping("/test/search/keyword/{keyword}")
	public SearchHits<BookDocument> searchKeyword(@PathVariable String keyword, @RequestParam int page) {
		Pageable pageable = PageRequest.of(page - 1, 10);
		return elasticSearchCustomBookRepository.searchProductsByProductName(keyword, pageable);
	}
}
