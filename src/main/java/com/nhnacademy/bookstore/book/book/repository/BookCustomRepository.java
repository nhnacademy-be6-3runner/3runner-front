package com.nhnacademy.bookstore.book.book.repository;

import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 도서 커스텀 레포지토리입니다.
 *
 * @author 김은비
 */
public interface BookCustomRepository {

    /**
     * 도서 리스트를 불러오는 메서드입니다.
     *
     * @param pageable 페이지
     * @return BookListResponse
     */
    Page<BookListResponse> readBookList(Pageable pageable);
}
