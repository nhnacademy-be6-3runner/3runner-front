package com.nhnacademy.bookstore.book.book.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NonNull;

import java.time.ZonedDateTime;


/**
 * book request form validate.
 *
 * @author 김병우
 * @param title
 * @param description
 * @param publishedDate
 * @param price
 * @param quantity
 * @param sellingPrice
 * @param viewCount
 * @param packing
 * @param author
 * @param isbn
 * @param publisher
 * @param createdAt
 */
@Builder
public record ReadBookResponse(
        @NonNull Long id,
        @NotBlank(message = "title is mandatory") String title,
        @NotBlank(message = "description is mandatory")  String description,
        @NotNull(message = "publishedDate is mandatory") ZonedDateTime publishedDate,
        @Min(value = 0, message = "price must be bigger then 0") int price,
        @Min(value = 0, message = "quantity must be bigger then 0") int quantity,
        @Min(value = 0, message = "sellingPrice must be bigger then 0") int sellingPrice,
        @Min(value = 0, message = "viewCount must be bigger then 0") int viewCount,
        @NotNull(message = "packing is mandatory") boolean packing,
        @NotBlank(message = "author is mandatory") String author,
        @NotBlank(message = "isbn is mandatory") String isbn,
        @NotBlank(message = "publisher is mandatory") String publisher,
        @NotNull(message = "createdAt is mandatory") ZonedDateTime createdAt) {
}
