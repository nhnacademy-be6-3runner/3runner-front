package com.nhnacademy.front.book.review.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record DeleteReviewRequest(
        @Size(min = 1, max = 500) String deletedReason
) {
}
