package com.nhnacademy.front.book.review.service.impl;

import com.nhnacademy.front.book.book.exception.InvalidApiResponseException;
import com.nhnacademy.front.book.review.controller.feign.ReviewClient;
import com.nhnacademy.front.book.review.dto.request.CreateReviewRequest;
import com.nhnacademy.front.book.review.dto.request.DeleteReviewRequest;
import com.nhnacademy.front.book.review.dto.request.UserCreateReviewRequest;
import com.nhnacademy.front.book.review.dto.response.ReviewAdminListResponse;
import com.nhnacademy.front.book.review.dto.response.ReviewDetailResponse;
import com.nhnacademy.front.book.review.dto.response.ReviewListResponse;
import com.nhnacademy.front.book.review.service.ReviewService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final ReviewClient reviewClient;

	@Override
	public Long createReview(long purchaseBookId, Long memberId, UserCreateReviewRequest request) {
		CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
			.title(request.title())
			.content(request.content())
			.imageList(contentToImageList(request.content()))
			.ratings(request.ratings())
			.build();
		log.info("리뷰 생성 : {}", createReviewRequest);
		ApiResponse<Long> response = reviewClient.createReview(purchaseBookId, memberId, createReviewRequest);
		return response.getBody().getData();
	}

	@Override
	public void updateReview(long reviewId, Long memberId, UserCreateReviewRequest request) {
		CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
			.title(request.title())
			.content(request.content())
			.imageList(contentToImageList(request.content()))
			.ratings(request.ratings())
			.build();
		ApiResponse<Long> response = reviewClient.updateReview(reviewId, memberId, createReviewRequest);
	}

	@Override
	public Page<ReviewAdminListResponse> readAllReviews(int page, int size) {
		ApiResponse<Page<ReviewAdminListResponse>> response = reviewClient.readReviewList(page, size);

		if (response.getHeader().isSuccessful() && response.getBody() != null) {
			return response.getBody().getData();
		} else {
			throw new InvalidApiResponseException("리뷰 페이지 조회 exception");
		}
	}

	@Override
	public Page<ReviewListResponse> readAllReviewsByBookId(Long bookId, int page, int size, String sort) {
		ApiResponse<Page<ReviewListResponse>> response = reviewClient.readAllReviewsByBookId(bookId, page, size, sort);

		if (response.getHeader().isSuccessful() && response.getBody() != null) {
			return response.getBody().getData();
		} else {
			throw new InvalidApiResponseException("리뷰 페이지 조회 exception");
		}
	}

	@Override
	public ReviewDetailResponse readReviewDetail(long reviewId) {
		ApiResponse<ReviewDetailResponse> response = reviewClient.readReviewDetail(reviewId);
		if (response.getHeader().isSuccessful() && response.getBody() != null) {
			return response.getBody().getData();
		} else {
			throw new InvalidApiResponseException("리뷰 상세 조회 exception");
		}
	}

	private List<String> contentToImageList(String content) {
		List<String> imageList = new ArrayList<>();
		String[] split = content.split("fileName=");
		if (split.length > 1) {
			for (int i = 1; i < split.length; i++) {
				imageList.add(split[i].substring(0, split[i].indexOf('"')));
			}
		}
		return imageList;
	}

	public Long countReviewsByBookId(long bookId) {
		ApiResponse<Long> response = reviewClient.countReviewsByBookId(bookId);
		return response.getBody().getData();
	}

	public Double getAverageRatingByBookId(long bookId) {
		ApiResponse<Double> response = reviewClient.avgReviewsByBookId(bookId);
		Double averageRating = response.getBody().getData();
		if (averageRating != null) {
			return Double.valueOf(String.format("%.2f", averageRating));
		}
		return 0.0;
	}

	@Override
	public Page<ReviewListResponse> readAllReviewsByMemberId(Long memberId, int page, int size) {
		ApiResponse<Page<ReviewListResponse>> response = reviewClient.readAllReviewsByMemberId(memberId, page, size);
		if (response.getHeader().isSuccessful() && response.getBody() != null) {
			return response.getBody().getData();
		} else {
			throw new InvalidApiResponseException("사용자 리뷰 조회 exception");
		}
	}

	@Override
	public void deleteReview(long reviewId, Long memberId, DeleteReviewRequest request) {
		reviewClient.deleteReview(reviewId, memberId, request);
	}
}
