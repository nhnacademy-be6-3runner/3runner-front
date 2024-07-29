package com.nhnacademy.front.book.review.service;

import com.nhnacademy.front.book.review.dto.request.DeleteReviewRequest;
import com.nhnacademy.front.book.review.dto.request.UserCreateReviewRequest;
import com.nhnacademy.front.book.review.dto.response.ReviewAdminListResponse;
import com.nhnacademy.front.book.review.dto.response.ReviewDetailResponse;
import com.nhnacademy.front.book.review.dto.response.ReviewListResponse;
import org.springframework.data.domain.Page;

public interface ReviewService {
	Long createReview(long purchaseBookId, Long memberId, UserCreateReviewRequest request);

	void updateReview(long reviewId, Long memberId, UserCreateReviewRequest request);

	Page<ReviewAdminListResponse> readAllReviews(int page, int size);

	Page<ReviewListResponse> readAllReviewsByBookId(Long bookId, int page, int size, String sort);

	ReviewDetailResponse readReviewDetail(long reviewId);

	Long countReviewsByBookId(long bookId);

	Double getAverageRatingByBookId(long bookId);

	Page<ReviewListResponse> readAllReviewsByMemberId(Long memberId, int page, int size);

	void deleteReview(long reviewId, Long memberId, DeleteReviewRequest request);
}
