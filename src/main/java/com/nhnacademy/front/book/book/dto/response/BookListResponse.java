package com.nhnacademy.front.book.book.dto.response;

import lombok.Builder;

@Builder
public record BookListResponse(
        long id, String title, int price, int sellingPrice, String author, String thumbnail
) {
}