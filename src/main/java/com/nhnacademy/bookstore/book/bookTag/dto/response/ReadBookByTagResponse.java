package com.nhnacademy.bookstore.book.bookTag.dto.response;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record ReadBookByTagResponse(String title, String description, ZonedDateTime publishedDate,
                                 int price, int account, int sellingPrice, int view_count,boolean packing,
                                 String author,String publisher,ZonedDateTime creationDate) {
}
