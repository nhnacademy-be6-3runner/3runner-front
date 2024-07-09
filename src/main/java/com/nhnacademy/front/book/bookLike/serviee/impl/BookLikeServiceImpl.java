package com.nhnacademy.front.book.bookLike.serviee.impl;

import com.nhnacademy.front.book.book.exception.NotFindBookException;
import com.nhnacademy.front.book.bookLike.controller.feign.BookLikeClient;
import com.nhnacademy.front.book.bookLike.serviee.BookLikeService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookLikeServiceImpl implements BookLikeService {
    private final BookLikeClient bookLikeClient;

    @Override
    public Long countLikeByBookId(Long bookId) {
        ApiResponse<Long> response = bookLikeClient.countLikeByBookId(bookId);
        if (!response.getHeader().isSuccessful()) {
            throw new NotFindBookException();
        }
        return response.getBody().getData();
    }
}
