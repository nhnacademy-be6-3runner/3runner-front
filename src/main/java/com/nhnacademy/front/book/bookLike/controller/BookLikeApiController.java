package com.nhnacademy.front.book.bookLike.controller;

import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.bookLike.serviee.BookLikeService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 도서 좋아요 api 컨트롤러입니다.
 *
 * @author 김은비
 */
@RestController
@RequiredArgsConstructor
public class BookLikeApiController {
	private final BookLikeService bookLikeService;

	/**
	 * 도서 좋아요 카운트 메서드입니다.
	 *
	 * @param bookId 책 아이디
	 * @return Api Response
	 */
	@GetMapping("/api/books/{bookId}/likes")
	public ApiResponse<Long> countLikeByBookId(@PathVariable("bookId") Long bookId) {
		Long cnt = bookLikeService.countLikeByBookId(bookId);
		return ApiResponse.success(cnt);
	}

	/**
	 * 도서 좋아요 생성 메서드입니다.
	 *
	 * @param bookId 책 아이디
	 * @param memberId 멤버 아이디
	 * @return Api Response
	 */
	@PostMapping("/api/books/{bookId}/likes")
	public ApiResponse<Void> createBookLike(@PathVariable("bookId") Long bookId,
		@RequestHeader(value = "Member-Id", required = false) Long memberId) {
		bookLikeService.createLikeBook(bookId, memberId);
		return new ApiResponse<>(new ApiResponse.Header(true, 200));
	}

	/**
	 * 도서 좋아요 삭제 메서드입니다.
	 *
	 * @param bookId 책 아이디
	 * @param memberId 멤버 아이디
	 * @return Api Response
	 */
	@DeleteMapping("/api/books/{bookId}/likes/delete")
	public ApiResponse<Void> deleteBookLike(@PathVariable("bookId") Long bookId,
		@RequestHeader(value = "Member-Id", required = false) Long memberId) {
		bookLikeService.deleteLikeBook(bookId, memberId);
		return new ApiResponse<>(new ApiResponse.Header(true, 200));
	}

	/**
	 * 도서 좋아요를 누른 적 있는지 확인하는 메서드입니다.
	 *
	 * @param bookId 책 아이디
	 * @param memberId 도서 아이디
	 * @return Api Response
	 */
	@GetMapping("/api/books/{bookId}/likes/status")
	public ApiResponse<Boolean> isBookLikeByMember(@PathVariable("bookId") Long bookId,
		@RequestHeader(value = "Member-Id", required = false) Long memberId) {
		if (memberId == null) {
			return ApiResponse.success(false);
		}
		Boolean isLiked = bookLikeService.isLikedByMember(bookId, memberId);
		return ApiResponse.success(isLiked);
	}

	/**
	 * 회원이 자신의 도서 좋아요 목록을 확인하는 메서드입니다.
	 *
	 * @param memberId 멤버 아이디
	 * @param page 페이지
	 * @param size 사이즈
	 * @return Api Response
	 */
	@GetMapping("/api/mypage/likes")
	public ApiResponse<Page<BookListResponse>> readAllBookLikesByMemberId(
		@RequestHeader(value = "Member-Id", required = false) Long memberId,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		Page<BookListResponse> response = bookLikeService.readAllBookLikesByMemberId(memberId, page, size);
		return ApiResponse.success(response);
	}
}
