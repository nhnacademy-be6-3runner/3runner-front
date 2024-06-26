package com.nhnacademy.front.book.book.service;

import com.nhnacademy.front.book.book.dto.request.UserCreateBookRequest;
import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.util.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.awt.print.Book;
import java.util.List;

public interface BookService {
    void saveBook(UserCreateBookRequest createBookRequest, String imageName);

    // TODO 리스트
    Page<BookListResponse> readLimitBooks(int limit);
}
