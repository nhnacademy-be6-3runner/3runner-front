package com.nhnacademy.front.book.book.service;

import com.nhnacademy.front.book.book.dto.request.UserCreateBookRequest;
import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.book.dto.response.UserReadBookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    void saveBook(UserCreateBookRequest createBookRequest, String imageName);

    /**
     * 메인 페이지에서 보여줄 도서 리스트입니다. 제한된 갯수로만 보여주게 되어있습니다.
     * @param limit 도서를 몇개까지 보여줄건지?
     * @return 도서 리스트
     */
    Page<BookListResponse> readLimitBooks(int limit);

    /**
     * 도서 페이지 조회 메서드입니다.
     * @param page 페이지
     * @param size 사이즈
     * @return 도서 리스트
     */
    Page<BookListResponse> readAllBooks(int page, int size);

    void saveApiBook(String isbn);

    UserReadBookResponse readBook(long bookId);
}