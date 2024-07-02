package com.nhnacademy.bookstore.book.book.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.bookstore.book.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.book.book.dto.response.ReadBookResponse;
import com.nhnacademy.bookstore.book.book.dto.response.UserReadBookResponse;
import com.nhnacademy.bookstore.book.book.exception.CreateBookRequestFormException;
import com.nhnacademy.bookstore.book.book.exception.UpdateBookRequestFormException;
import com.nhnacademy.bookstore.book.book.service.BookService;
import com.nhnacademy.bookstore.book.bookCartegory.service.BookCategoryService;
import com.nhnacademy.bookstore.book.bookImage.service.BookImageService;
import com.nhnacademy.bookstore.book.bookTag.dto.request.ReadBookIdRequest;
import com.nhnacademy.bookstore.book.bookTag.dto.response.ReadTagByBookResponse;
import com.nhnacademy.bookstore.book.bookTag.service.BookTagService;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.bookstore.util.ApiResponse;
import com.nhnacademy.bookstore.util.ValidationUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 책 요청 컨트롤러.
 *
 * @author 김병우
 * fix 한민기
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookstore/books")
@Slf4j
public class BookController {
	private final BookService bookService;
	private final BookImageService bookImageService;
	private final BookTagService bookTagService;
	private final BookCategoryService bookCategoryService;

	/**
	 * 책 등록 요청 처리.
	 *
	 * @param createBookRequest request form
	 * @param bindingResult     binding result
	 * @return ApiResponse<>
	 */
	@PostMapping
	public ApiResponse<Void> createBook(@Valid @RequestBody CreateBookRequest createBookRequest,
		BindingResult bindingResult) {
		ValidationUtils.validateBindingResult(bindingResult,
			new CreateBookRequestFormException("책의 항목들의 범위가 잘못되었습니다."));

		bookService.createBook(createBookRequest);

		return new ApiResponse<>(new ApiResponse.Header(true, 201));
	}

	@GetMapping
	public ApiResponse<Page<BookListResponse>> readAllBooks(@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<BookListResponse> bookList = bookService.readAllBooks(pageable);
		return new ApiResponse<>(
			new ApiResponse.Header(true, 200),
			new ApiResponse.Body<>(bookList)
		);
	}

	@GetMapping("/{bookId}")
	public ApiResponse<UserReadBookResponse> readBook(@PathVariable("bookId") Long bookId) {
		ReadBookResponse detailBook = bookService.readBookById(bookId);
		List<CategoryParentWithChildrenResponse> categoryList = bookCategoryService.readBookWithCategoryList(bookId);

		List<ReadTagByBookResponse> tagList =
			bookTagService.readTagByBookId(
				ReadBookIdRequest.builder().bookId(bookId).build());

		UserReadBookResponse book = UserReadBookResponse.builder()
			.id(detailBook.id())
			.title(detailBook.title())
			.description(detailBook.description())
			.publishedDate(detailBook.publishedDate())
			.price(detailBook.price())
			.quantity(detailBook.quantity())
			.sellingPrice(detailBook.sellingPrice())
			.viewCount(detailBook.viewCount())
			.packing(detailBook.packing())
			.author(detailBook.author())
			.isbn(detailBook.isbn())
			.publisher(detailBook.publisher())
			.imagePath(detailBook.imagePath())
			.categoryList(categoryList)
			.tagList(tagList)
			.build();

		return ApiResponse.success(book);
	}

	/**
	 * 책 수정 관련 컨트롤러
	 * @param bookId 수정할 책의 아이디
	 * @param createBookRequest 수정 받아 올 정보
	 * @param bindingResult 정보를 검증할 내용
	 * @return 완료 코드
	 */
	@PutMapping("/{bookId}")
	public ApiResponse<Void> updateBook(@PathVariable Long bookId,
		@Valid @RequestBody CreateBookRequest createBookRequest, BindingResult bindingResult) {

		ValidationUtils.validateBindingResult(bindingResult,
			new UpdateBookRequestFormException("책의 항목들의 범위가 잘못되었습니다."));

		bookService.updateBook(bookId, createBookRequest);

		return new ApiResponse<>(new ApiResponse.Header(true, 200));
	}
}