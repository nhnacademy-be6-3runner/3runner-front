package com.nhnacademy.front.book.review.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record DeleteReviewRequest(
        @Size(min = 1, max = 500) @NotNull String deletedReason
) {
}
