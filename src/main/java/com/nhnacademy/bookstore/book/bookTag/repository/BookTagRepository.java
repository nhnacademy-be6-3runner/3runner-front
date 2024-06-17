package com.nhnacademy.bookstore.book.bookTag.repository;

import com.nhnacademy.bookstore.entity.bookTag.BookTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTagRepository extends JpaRepository<BookTag, Long> {
}
