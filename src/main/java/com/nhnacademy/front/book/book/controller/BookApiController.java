package com.nhnacademy.front.book.book.controller;

import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.front.book.book.dto.response.BookManagementResponse;
import com.nhnacademy.front.book.book.service.BookService;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Slf4j
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

	@GetMapping("/main")
	public ApiResponse<Page<BookListResponse>> readLimitBooks(@RequestParam(defaultValue = "publishedDate,desc") String sort) {
		Page<BookListResponse> bookList = bookService.readAllBooks(0, 12, sort);
		log.info("정렬 조회 컨트롤러 넘어옴");
		return ApiResponse.success(bookList);
	}
}
