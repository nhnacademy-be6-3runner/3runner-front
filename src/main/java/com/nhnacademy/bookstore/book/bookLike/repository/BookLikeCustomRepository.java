package com.nhnacademy.bookstore.book.bookLike.repository;

import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.entity.bookLike.BookLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookLikeCustomRepository {
  // TODO 회원 아이디로 좋아요한 도서 목록 조회 (최신순)
  Page<BookListResponse> findBookLikeByMemberId(long memberId, Pageable pageable);

  // TODO 도서의 좋아요 count 조회
  long countLikeByBookId(long bookId);

  // TODO 좋아요가 많은 순으로 도서 조회 -> 도서 쪽에서 해야하나?
  Page<BookListResponse> findBooksOrderByLikes(Pageable pageable);

  // TODO 회원이 좋아요한 최신순으로 도서 리스트 조회 (정렬)
}
