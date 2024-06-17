package com.nhnacademy.bookstore.book.bookLike.repository;

import com.nhnacademy.bookstore.entity.bookLike.BookLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLikeRepository extends JpaRepository<BookLike, Long> {
}
