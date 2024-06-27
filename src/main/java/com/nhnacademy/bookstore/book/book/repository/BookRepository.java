package com.nhnacademy.bookstore.book.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhnacademy.bookstore.entity.book.Book;

/**
 * 책 Repository.
 *
 * @author 김병우
 */
public interface BookRepository extends JpaRepository<Book, Long>, BookCustomRepository {

}
