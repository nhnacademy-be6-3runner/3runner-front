package com.nhnacademy.bookstore.book.bookLike.repository;

import com.nhnacademy.bookstore.entity.bookLike.BookLike;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 도서-좋아요 기능을 위한 repository.
 * @author 김은비
 */
public interface BookLikeRepository extends JpaRepository<BookLike, Long>, BookLikeCustomRepository {
}
