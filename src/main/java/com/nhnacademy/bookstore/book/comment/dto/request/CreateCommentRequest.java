package com.nhnacademy.bookstore.book.comment.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateCommentRequest(
        @Size(min = 1, max = 100) String content
) {
}
