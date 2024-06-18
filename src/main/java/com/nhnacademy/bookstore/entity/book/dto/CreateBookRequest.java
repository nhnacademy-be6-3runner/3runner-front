package com.nhnacademy.bookstore.entity.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

public record CreateBookRequest(
        @NotBlank(message = "title is entry") String title,
        @NotBlank String description,
        @DateTimeFormat ZonedDateTime publishedDate,
        @Min(0) int price,
        @Min(0) int quantity,
        @Min(0) int sellingPrice,
        @Min(0) int viewCount,
        @NotNull boolean packing,
        @NotBlank String author,
        @NotBlank String isbn,
        @NotBlank String publisher,
        @DateTimeFormat ZonedDateTime createdAt) {
}
