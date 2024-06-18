package com.nhnacademy.bookstore.book.book.service;

import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.entity.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 책 서비스 구현체
 * @author 김병우
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    /**
     * 책 등록 기능
     * @param book
     */
    @Override
    public void createBook(Book book) {
        bookRepository.save(book);
    }


}
