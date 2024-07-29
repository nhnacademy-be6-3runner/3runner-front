package com.nhnacademy.front.book.book.controller.feign;

import com.nhnacademy.front.book.book.dto.request.CreateBookRequest;
import com.nhnacademy.front.book.book.dto.response.BookDocumentResponse;
import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.book.dto.response.BookManagementResponse;
import com.nhnacademy.front.book.book.dto.response.UserReadBookResponse;
import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 도서 feign client 입니다.
 *
 * @author 한민기, 김은비
 */
@FeignClient(name = "BookClient", url = "${feign.client.url}/bookstore/books")
public interface BookClient {
	@PostMapping
	ApiResponse<Void> createBook(@RequestBody CreateBookRequest createBookRequest);

	/**
	 * 도서 페이지 조회 메서드입니다.
	 *
	 * @param page 페이지
	 * @param size 사이즈
	 * @return 도서 리스트
	 * @author 김은비
	 */
	@GetMapping
	ApiResponse<Page<BookListResponse>> readAllBooks(@RequestParam("page") int page, @RequestParam("size") int size,
		@RequestParam("sort") String sort);

	/**
	 * 책의 상세조회 메서드입니다.
	 *
	 * @param bookId 책의 아이디
	 * @return 책에 관련된 내용
	 */
	@GetMapping("/{bookId}")
	ApiResponse<UserReadBookResponse> getDetailBookById(@PathVariable("bookId") Long bookId);

	/**
	 * 책 수정 메서드 입니다.
	 *
	 * @param bookId 책의 아이디
	 * @param createBookRequest 책의 수정 내용
	 * @return 성공 여부
	 */
	@PutMapping("/{bookId}")
	ApiResponse<Void> updateBook(@PathVariable("bookId") Long bookId, @RequestBody CreateBookRequest createBookRequest);

	/**
	 * 책의 관리자 페이지에서 리스트 보기 메소드입니다.
	 *
	 * @param page 책의 페이지
	 * @param size 페이지 사이즈
	 * @return 책 리스트
	 */
	@GetMapping("/admin")
	ApiResponse<Page<BookManagementResponse>> readAllAdminBooks(@RequestParam("page") int page,
		@RequestParam("size") int size);

	/**
	 * 책의 삭제관련 메소드 입니다.
	 *
	 * @param bookId 삭제할 책의 id
	 * @return 삭제 완료 코드
	 */
	@DeleteMapping("/{bookId}")
	ApiResponse<Void> deleteBook(@PathVariable("bookId") Long bookId);

	/**
	 * 책 검색 결과를 가져오는 메소드 입니다.
	 *
	 * @param page 페이지
	 * @param size 페이지 사이즈
	 * @param keyword 겸색 키워드
	 * @return 검색 결과
	 */
	@GetMapping("/search")
	ApiResponse<Page<BookDocumentResponse>> searchReadAllBooks(@RequestParam("page") int page,
		@RequestParam("size") int size, @RequestParam("keyword") String keyword);
}
