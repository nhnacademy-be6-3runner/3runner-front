package com.nhnacademy.bookstore.book.bookCartegory.repository;

import com.nhnacademy.bookstore.entity.bookCategory.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long>, BookCategoryCustomRepository {

}
