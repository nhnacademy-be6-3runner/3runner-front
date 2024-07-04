package com.nhnacademy.front.book.book.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.front.book.book.dto.response.BookManagementResponse;
import com.nhnacademy.front.book.book.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookApiController {
	private final BookService bookService;

	@GetMapping
	public ResponseEntity<Page<BookManagementResponse>> getBooks(@RequestParam int page) {

		Page<BookManagementResponse> bookPage = bookService.readAllAdminBooks(page, 20);
		return ResponseEntity.ok(bookPage);
	}

}
