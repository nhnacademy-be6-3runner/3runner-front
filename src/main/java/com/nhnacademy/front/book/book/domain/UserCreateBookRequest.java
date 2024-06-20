package com.nhnacademy.front.book.book.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;

public record UserCreateBookRequest(
        @NotBlank(message = "title is entry") String title,
        @NotBlank String description,
        @DateTimeFormat ZonedDateTime publishedDate,
        @Min(0) int price,
        @Min(0) int quantity,
        @Min(0) int sellingPrice,

        MultipartFile image,

        @NotNull boolean packing,
        @NotBlank String author,
        @NotBlank String isbn,
        @NotBlank String publisher,
        @DateTimeFormat ZonedDateTime createdAt) {
}
