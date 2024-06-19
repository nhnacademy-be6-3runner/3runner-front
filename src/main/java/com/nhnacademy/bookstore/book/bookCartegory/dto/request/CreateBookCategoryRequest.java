package com.nhnacademy.bookstore.book.bookCartegory.dto.request;

import lombok.Builder;

@Builder
public record CreateBookCategoryRequest(long bookId, long categoryId) {
}
