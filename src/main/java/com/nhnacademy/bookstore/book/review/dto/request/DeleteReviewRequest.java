package com.nhnacademy.bookstore.book.review.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * 리뷰 삭제 dto 입니다.
 *
 * @param deleteReason 삭제 사유
 * @author 김은비
 */
@Builder
public record DeleteReviewRequest(
        @Size(min = 1, max = 100) String deleteReason
) {
}
