package com.nhnacademy.front.book.review.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record UserCreateReviewRequest(
        @Size(min = 1, max = 50) String title,
        @Size(min = 1, max = 200) String content,
        @NotNull double ratings,
        MultipartFile image
) {
}
