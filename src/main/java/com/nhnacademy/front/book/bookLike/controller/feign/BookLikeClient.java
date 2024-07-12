package com.nhnacademy.front.book.bookLike.controller.feign;

import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "BookReview", url = "http://${feign.client.url}/bookstore")
public interface BookLikeClient {
    @GetMapping("/{bookId}/likes")
    ApiResponse<Long> countLikeByBookId(@PathVariable("bookId") Long bookId);

    @PostMapping("/{bookId}/like")
    ApiResponse<Void> createBookLike(@PathVariable("bookId") Long bookId, @RequestHeader(value = "Member-Id", required = false) Long memberId);

    @DeleteMapping("/{bookId}/like/delete")
    ApiResponse<Void> deleteBookLike(@PathVariable("bookId") Long bookId, @RequestHeader(value = "Member-Id", required = false) Long memberId);

    @GetMapping("/{bookId}/likes/status")
    ApiResponse<Boolean> isLikedByMember(@PathVariable("bookId") Long bookId, @RequestHeader(value = "Member-Id", required = false) Long memberId);

    @GetMapping("/mypage/books/likes")
    ApiResponse<Page<BookListResponse>> readAllBookLikesByMemberId (@RequestHeader(value = "Member-Id", required = false) Long memberId,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size);
}
