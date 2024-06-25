package com.nhnacademy.bookstore.book.book.controller;

import com.nhnacademy.bookstore.book.book.dto.response.ReadBookResponse;
import com.nhnacademy.bookstore.book.book.exception.CreateBookRequestFormException;
import com.nhnacademy.bookstore.book.book.service.BookService;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.book.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstore.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 책 요청 컨트롤러.
 *
 * @author 김병우
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookstore/books")
public class BookController {
	private final BookService bookService;
	private final BookImageService bookImageService;
	private final BookTagService bookTagService;
	private final BookCategoryService bookCategoryService;

	/**
	 * 책 등록 요청 처리.
	 *
	 * @param createBookRequest request form
	 * @param bindingResult binding result
	 * @return ApiResponse<>
	 */
	@Transactional
	@PostMapping
	public ApiResponse<Void> createBook(@Valid @RequestBody CreateBookRequest createBookRequest,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new CreateBookRequestFormException(bindingResult.getFieldErrors().toString());
		}
		long bookId = bookService.createBook(createBookRequest);
		bookCategoryService.createBookCategory(
			CreateBookCategoryRequest.builder().bookId(bookId).categoryIds(createBookRequest.categoryIds()).build());
		bookTagService.createBookTag(
			CreateBookTagListRequest.builder().bookId(bookId).tagIdList(createBookRequest.categoryIds()).build());
		bookImageService.createBookImage(createBookRequest.imageList(), bookId, BookImageType.DESCRIPTION);
		if (!Objects.isNull(createBookRequest.imageName())) {
			bookImageService.createBookImage(List.of(createBookRequest.imageName()), bookId, BookImageType.MAIN);
		}

		return new ApiResponse<>(new ApiResponse.Header(true, 201));
	}

	@GetMapping("/{bookId}")
	public ApiResponse<ReadBookResponse> readBook(@PathVariable("bookId") Long bookId) {
		ReadBookResponse book = bookService.readBookById(bookId);

		return new ApiResponse<ReadBookResponse>(
			new ApiResponse.Header(true, 200),
			new ApiResponse.Body<ReadBookResponse>(book)
		);
	}
}

