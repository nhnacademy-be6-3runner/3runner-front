package com.nhnacademy.bookstore.bookCategory.repository;

import com.nhnacademy.bookstore.book.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.book.bookCartegory.repository.BookCategoryRepository;
import com.nhnacademy.bookstore.book.bookCartegory.repository.impl.BookCategoryCustomRepositoryImpl;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.bookCategory.BookCategory;
import com.nhnacademy.bookstore.entity.bookCategory.QBookCategory;
import com.nhnacademy.bookstore.entity.category.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.ZonedDateTime;

@DataJpaTest
@Import(BookCategoryCustomRepositoryImpl.class)
public class BookCategoryRepositoryTest {
    @Autowired
    private BookCategoryRepository bookCategoryRepository;
    private BookRepository bookRepository;
    private Book book;
    private Category category;
    private BookCategory bookCategory;

    @BeforeEach
    public void setup() {

    }

//    @DisplayName("도서에 해당하는 카테고리 모두 삭제")
//    @Test
//    void deletedByBookTest() {
//        book = new Book("name", "desc", ZonedDateTime.now(), 10000, 8000, 1, 1, true, 12345678, "김은비", null, null, null);
//
//    }

}
