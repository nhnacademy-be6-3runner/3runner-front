package com.nhnacademy.bookstore.book.book.service.impl;

import com.nhnacademy.bookstore.book.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstore.book.book.exception.BookDoesNotExistException;
import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.book.book.service.BookService;
import com.nhnacademy.bookstore.entity.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 책 서비스 구현체.
 *
 * @author 김병우
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    /**
     * 책 등록 기능
     *
     * @param createBookRequest createBookRequest form
     */
    // Dto -> save book
    @Override
    public void createBook(CreateBookRequest createBookRequest) {
        bookRepository.save(new Book(
                createBookRequest.title(),
                createBookRequest.description(),
                createBookRequest.publishedDate(),
                createBookRequest.price(),
                createBookRequest.quantity(),
                createBookRequest.sellingPrice(),
                createBookRequest.viewCount(),
                createBookRequest.packing(),
                createBookRequest.author(),
                createBookRequest.isbn(),
                createBookRequest.publisher(),
                null,
                null,
                null
        ));
    }

    /**
     * 책 조회 기능
     *
     * @param bookId book entity id
     */
    @Override
    public Book readBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(()-> new BookDoesNotExistException("책이 존재하지 않습니다"));
    }
}
