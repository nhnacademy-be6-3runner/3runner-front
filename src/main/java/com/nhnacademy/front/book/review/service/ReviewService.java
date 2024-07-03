package com.nhnacademy.front.book.review.service;

import com.nhnacademy.front.book.review.dto.request.UserCreateReviewRequest;

public interface ReviewService {
    Long createReview(long purchaseBookId, long memberId, UserCreateReviewRequest request, String imageName);
}
