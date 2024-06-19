package com.nhnacademy.bookstore.book.bookCartegory.dto.response;

import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.category.Category;
import lombok.Builder;

@Builder
public record BookCategoryResponse(Book book, Category category) {
}
