package com.nhnacademy.front.book.bookLike.serviee;

import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import org.springframework.data.domain.Page;

public interface BookLikeService {
    Long countLikeByBookId(Long bookId);
    void createLikeBook(Long bookId, Long memberId);
    void deleteLikeBook(Long bookId, Long memberId);
    boolean isLikedByMember(Long bookId, Long memberId);
    Page<BookListResponse> readAllBookLikesByMemberId(Long memberId, int page, int size);
}
