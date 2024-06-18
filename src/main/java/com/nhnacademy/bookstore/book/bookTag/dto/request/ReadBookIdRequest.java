package com.nhnacademy.bookstore.book.bookTag.dto.request;

import lombok.Builder;

@Builder
public record ReadBookIdRequest(long bookId) {
}
