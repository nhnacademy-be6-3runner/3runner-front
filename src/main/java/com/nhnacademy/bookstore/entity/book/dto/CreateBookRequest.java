package com.nhnacademy.bookstore.entity.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

public record CreateBookRequest(
        @NotBlank(message = "title is mandatory") String title,
        @NotBlank(message = "description is mandatory")  String description,
        @NotNull(message = "publishedDate is mandatory") ZonedDateTime publishedDate,
        @Min(value = 0,message = "price must be bigger then 0") int price,
        @Min(value = 0,message = "quantity must be bigger then 0") int quantity,
        @Min(value = 0,message = "sellingPrice must be bigger then 0") int sellingPrice,
        @Min(value = 0,message = "viewCount must be bigger then 0") int viewCount,
        @NotNull(message = "packing is mandatory") boolean packing,
        @NotBlank(message = "author is mandatory") String author,
        @NotBlank(message = "isbn is mandatory") String isbn,
        @NotBlank(message = "publisher is mandatory") String publisher,
        @NotNull(message = "createdAt is mandatory") ZonedDateTime createdAt) {
}
