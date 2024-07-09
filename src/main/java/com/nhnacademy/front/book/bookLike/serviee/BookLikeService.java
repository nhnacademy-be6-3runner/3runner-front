package com.nhnacademy.front.book.bookLike.serviee;

public interface BookLikeService {
    Long countLikeByBookId(Long bookId);
    void createLikeBook(Long bookId, Long memberId);
    void deleteLikeBook(Long bookId, Long memberId);
    boolean isLikedByMember(Long bookId, Long memberId);
}
