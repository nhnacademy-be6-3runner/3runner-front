package com.nhnacademy.bookstore.book.bookCartegory.repository;

import com.nhnacademy.bookstore.book.bookCartegory.dto.response.BookCategoryResponse;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.bookCategory.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BookCategoryRepository extends JpaRepository<BookCategory, Long>, BookCategoryCustomRepository {
    void deleteByBook(Book book);
}
