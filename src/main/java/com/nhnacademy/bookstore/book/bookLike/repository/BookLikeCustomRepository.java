package com.nhnacademy.bookstore.book.bookLike.repository;

import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.entity.bookLike.BookLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookLikeCustomRepository {
  // TODO 회원 아이디로 좋아요한 도서 목록 조회
  Page<BookListResponse> findBookLikeByMemberId(Long memberId, Pageable pageable);

  // TODO 도서의 좋아요 count 조회
  long countLikeByBookId(long bookId);

  // TODO 좋아요가 많은 순으로 도서 조회 -> 도서 쪽에서 해야하나?
  Page<BookListResponse> findBooksOrderByLike(Pageable pageable);
}
