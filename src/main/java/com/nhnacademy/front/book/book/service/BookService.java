package com.nhnacademy.front.book.book.service;

import com.nhnacademy.front.book.book.dto.request.UserCreateBookRequest;
import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.book.dto.response.UserReadBookResponse;
import org.springframework.data.domain.Page;

public interface BookService {
    void saveBook(UserCreateBookRequest createBookRequest, String imageName);

    Page<BookListResponse> readLimitBooks(int limit);
    void saveApiBook(String isbn);

    UserReadBookResponse readBook(long bookId);
}