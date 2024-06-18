package com.nhnacademy.bookstore.book.book.service;

import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.entity.book.Book;

/**
 * 책 테이블 CRUD 서비스
 * @author 김병우
 */
public interface BookService {
    /**
     * 책 등록 기능
     * @param book
     */
    public void createBook(Book book);

    public Book readBookById(Long bookId);
}
