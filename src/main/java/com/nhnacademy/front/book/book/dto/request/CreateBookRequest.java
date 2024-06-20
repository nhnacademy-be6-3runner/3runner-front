package com.nhnacademy.front.book.book.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
public record CreateBookRequest(
        @NotBlank(message = "title is mandatory") String title,
        @NotBlank(message = "description is mandatory")  String description,
        @NotNull(message = "publishedDate is mandatory") ZonedDateTime publishedDate,
        @Min(value = 0, message = "price must be bigger then 0") int price,
        @Min(value = 0, message = "quantity must be bigger then 0") int quantity,
        @Min(value = 0, message = "sellingPrice must be bigger then 0") int sellingPrice,
        @NotNull(message = "packing is mandatory") boolean packing,
        @NotBlank(message = "author is mandatory") String author,
        @NotBlank(message = "isbn is mandatory") String isbn,
        @NotBlank(message = "publisher is mandatory") String publisher,

        MultipartFile image,

        List<String> imageList,
        @NotBlank(message = "tag is mandatory")List<Long> tagIds,
        @NotBlank(message = "category is mandatory")List<Long> categoryIds) {
}
