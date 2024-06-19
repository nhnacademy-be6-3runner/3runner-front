package com.nhnacademy.bookstore.book.book.repository;

import com.nhnacademy.bookstore.entity.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {


}
