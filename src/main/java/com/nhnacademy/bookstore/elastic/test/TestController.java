package com.nhnacademy.bookstore.elastic.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.bookstore.book.book.dto.response.ReadBookResponse;
import com.nhnacademy.bookstore.book.book.service.BookService;
import com.nhnacademy.bookstore.elastic.book.repository.ElasticSearchBookRepository;
import com.nhnacademy.bookstore.elastic.document.book.BookDocument;

@RestController
public class TestController {

	@Autowired
	ElasticSearchBookRepository elasticSearchBookRepository;

	@Autowired
	BookService bookService;

	@GetMapping("/test/push")
	String push() {

		for (long i = 11; i <= 20; i++) {
			ReadBookResponse book = bookService.readBookById(i);

			BookDocument bookDocument = new BookDocument(
				book.id(),
				book.title(),
				book.author(),
				book.imagePath(),
				book.publisher(),
				book.publishedDate().toString(),
				List.of("역사", "과학"),
				List.of("국내도서")
			);
			elasticSearchBookRepository.save(bookDocument);
		}

		return "good";
	}
}
