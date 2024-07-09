package com.nhnacademy.front.book.bookLike.serviee.impl;

import com.nhnacademy.front.book.book.exception.NotFindBookException;
import com.nhnacademy.front.book.bookLike.controller.feign.BookLikeClient;
import com.nhnacademy.front.book.bookLike.exception.CountBookLikeException;
import com.nhnacademy.front.book.bookLike.exception.CreateBookLikeException;
import com.nhnacademy.front.book.bookLike.exception.DeleteBookLikeException;
import com.nhnacademy.front.book.bookLike.serviee.BookLikeService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookLikeServiceImpl implements BookLikeService {
    private final BookLikeClient bookLikeClient;

    @Override
    public Long countLikeByBookId(Long bookId) {
        ApiResponse<Long> response = bookLikeClient.countLikeByBookId(bookId);
        if (!response.getHeader().isSuccessful()) {
            throw new CountBookLikeException();
        }
        log.info("book Id, and Likes: {}, {}", bookId, response.getBody().getData());
        return response.getBody().getData();
    }

    @Override
    public void createLikeBook(Long bookId, Long memberId) {
        ApiResponse<Void> response = bookLikeClient.createBookLike(bookId, memberId);
        if (!response.getHeader().isSuccessful()) {
            throw new CreateBookLikeException();
        }
    }

    @Override
    public void deleteLikeBook(Long bookId, Long memberId) {
        ApiResponse<Void> response = bookLikeClient.deleteBookLike(bookId, memberId);
        if (!response.getHeader().isSuccessful()) {
            throw new DeleteBookLikeException();
        }
    }

    @Override
    public boolean isLikedByMember(Long bookId, Long memberId) {
        ApiResponse<Boolean> response = bookLikeClient.isLikedByMember(bookId, memberId);
        if (!response.getHeader().isSuccessful()) {
            throw new CountBookLikeException();
        }
        return response.getBody().getData();
    }
}
