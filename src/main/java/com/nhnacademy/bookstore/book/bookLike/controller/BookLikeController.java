package com.nhnacademy.bookstore.book.bookLike.controller;

import com.nhnacademy.bookstore.book.bookLike.service.BookLikeService;
import com.nhnacademy.bookstore.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 도서 좋아요 컨트롤러입니다.
 *
 * @author 김은비
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookstore")
public class BookLikeController {
    private final BookLikeService bookLikeService;

    /**
     * 좋아요 생성 메서드입니다.
     *
     * @param bookId   도서 아이디
     * @param memberId 멤버 아이디
     */
    @PostMapping("/{bookId}/like")
    public ApiResponse<Void> createBookLike(@PathVariable Long bookId, @RequestHeader("Member-Id") Long memberId) {
        bookLikeService.createBookLike(bookId, memberId);
        return new ApiResponse<>(new ApiResponse.Header(true, 200));
    }

    /**
     * 도서 좋아요 카운트 메서드입니다.
     *
     * @param bookId 도서 아이디
     * @return 카운트
     */
    @GetMapping("/{bookId}/likes")
    public ApiResponse<Long> countLikeByBookId(@PathVariable Long bookId) {
        long cnt = bookLikeService.countLikeByBookId(bookId);
        return ApiResponse.success(cnt);
    }

    /**
     * 도서 좋아요 삭제 메서드입니다.
     *
     * @param bookId   도서 아이디
     * @param memberId 멤버 아이디
     */
    @DeleteMapping("/{bookId}/like/delete")
    public ApiResponse<Void> deleteBookLike(@PathVariable Long bookId, @RequestHeader("Member-Id") Long memberId) {
        bookLikeService.deleteBookLike(bookId, memberId);
        return new ApiResponse<>(new ApiResponse.Header(true, 200));
    }
}
