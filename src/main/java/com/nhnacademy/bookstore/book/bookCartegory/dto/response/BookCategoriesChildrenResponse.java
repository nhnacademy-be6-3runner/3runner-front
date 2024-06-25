package com.nhnacademy.bookstore.book.bookCartegory.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record BookCategoriesChildrenResponse(long id, String name, List<BookCategoriesChildrenResponse> children) {
}
