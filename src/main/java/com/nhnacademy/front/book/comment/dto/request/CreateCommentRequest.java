package com.nhnacademy.front.book.comment.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateCommentRequest (
        @Size(min = 5, max = 100) String content
) {
}
