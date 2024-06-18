package com.nhnacademy.bookstore.book.book.service;

import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.entity.book.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
@ComponentScan(basePackages = "com.nhnacademy.bookstore")
class BookServiceImplTest {
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @DisplayName("책 등록 테스트")
    @Test
    public void testCreateBook() {
        //given
        Book book =new Book(
                        "title",
                        "desciprtion",
                        ZonedDateTime.now(),
                        1000,
                        1,
                        900,
                        1,
                        false,
                        "author",
                        "123123",
                        "1233123",
                        null,
                        null,
                        null
                );

        // when
        bookService.createBook(book);

        // then
        verify(bookRepository, times(1)).save(book);
    }
}