package com.nhnacademy.front.book.book.controller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.book.book.dto.request.CreateBookRequest;
import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.book.dto.response.BookManagementResponse;
import com.nhnacademy.front.book.book.dto.response.UserReadBookResponse;
import com.nhnacademy.front.util.ApiResponse;

/**
 * 도서 feign client 입니다.
 *
 * @author 한민기, 김은비
 */
@FeignClient(name = "BookClient", url = "http://${feign.client.url}/bookstore/books")
public interface BookClient {
	@PostMapping
	ApiResponse<Void> createBook(@RequestBody CreateBookRequest createBookRequest);

	/**
	 * 도서 페이지 조회 메서드입니다.
	 * @param page 페이지
	 * @param size 사이즈
	 * @return 도서 리스트
	 * @author 김은비
	 */
	@GetMapping
	ApiResponse<Page<BookListResponse>> readAllBooks(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort);

	@GetMapping("/{bookId}")
	ApiResponse<UserReadBookResponse> getDetailBookById(@PathVariable("bookId") Long bookId);

	@PutMapping("/{bookId}")
	ApiResponse<Void> updateBook(@PathVariable("bookId") Long bookId, @RequestBody CreateBookRequest createBookRequest);

	@GetMapping("/admin")
	ApiResponse<Page<BookManagementResponse>> readAllAdminBooks(@RequestParam("page") int page,
		@RequestParam("size") int size);

	@DeleteMapping("/{bookId}")
	ApiResponse<Void> deleteBook(@PathVariable("bookId") Long bookId);
}
