package com.nhnacademy.front.book.book.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.front.book.book.dto.response.BookDocumentResponse;
import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.book.dto.response.BookManagementResponse;
import com.nhnacademy.front.book.book.service.BookService;
import com.nhnacademy.front.util.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	public ApiResponse<Page<BookListResponse>> readAllBooks(@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "12") int size, @RequestParam(defaultValue = "publishedDate,desc") String sort) {
		Page<BookListResponse> bookList = bookService.readAllBooks(page, size, sort);
		return ApiResponse.success(bookList);
	}

	/**
	 * 검색 불러오기
	 * @param page 페이지
	 * @param size 페이지 사이즈
	 * @param keyword 검색 키워드
	 * @return 검색 결과 내용을 page 로
	 */
	@GetMapping("/search")
	public ApiResponse<Page<BookDocumentResponse>> readSearchBooks(@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "12") int size, @RequestParam(name = "keyword") String keyword) {
		Page<BookDocumentResponse> bookPage = bookService.searchReadAllBooks(keyword, page, size);
		return ApiResponse.success(bookPage);
	}

	@GetMapping("/category")
	public ApiResponse<Page<BookListResponse>> readAllBooks(@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "12") int size, @RequestParam(defaultValue = "viewCount,desc") String sort,
		@RequestParam(defaultValue = "1") int categoryId) {
		Page<BookListResponse> bookList = bookService.readCategoryAllBooks(page, size, sort, categoryId);
		return ApiResponse.success(bookList);
	}
}
