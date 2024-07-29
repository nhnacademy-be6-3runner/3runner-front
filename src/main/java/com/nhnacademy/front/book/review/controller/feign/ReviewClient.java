package com.nhnacademy.front.book.review.controller.feign;

import com.nhnacademy.front.book.review.dto.request.CreateReviewRequest;
import com.nhnacademy.front.book.review.dto.request.DeleteReviewRequest;
import com.nhnacademy.front.book.review.dto.response.ReviewAdminListResponse;
import com.nhnacademy.front.book.review.dto.response.ReviewDetailResponse;
import com.nhnacademy.front.book.review.dto.response.ReviewListResponse;
import com.nhnacademy.front.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ReviewClient", url = "${feign.client.url}/bookstore")
public interface ReviewClient {
	@PostMapping("/{purchaseBookId}/create")
	ApiResponse<Long> createReview(@PathVariable long purchaseBookId,
		@RequestHeader(value = "Member-Id", required = false) Long memberId,
		@Valid @RequestBody CreateReviewRequest createReviewRequest);

	@GetMapping("/reviews/{reviewId}")
	ApiResponse<ReviewDetailResponse> readReviewDetail(@PathVariable long reviewId);

	@PutMapping("/review/{reviewId}")
	ApiResponse<Long> updateReview(@PathVariable long reviewId,
		@RequestHeader(value = "Member-Id", required = false) Long memberId,
		@Valid @RequestBody CreateReviewRequest createReviewRequest);

	@PutMapping("/reviews/{reviewId}/delete")
	ApiResponse<Void> deleteReview(@PathVariable long reviewId,
		@RequestHeader(value = "Member-Id", required = false) Long memberId,
		@Valid @RequestBody DeleteReviewRequest deleteReviewRequest);

	@GetMapping("/reviews")
	ApiResponse<Page<ReviewAdminListResponse>> readReviewList(@RequestParam int page, @RequestParam int size);

	@GetMapping("/books/{bookId}/reviews")
	ApiResponse<Page<ReviewListResponse>> readAllReviewsByBookId(
		@PathVariable long bookId,
		@RequestParam int page,
		@RequestParam int size,
		@RequestParam String sort
	);

	@GetMapping("/reviews/member")
	ApiResponse<Page<ReviewListResponse>> readAllReviewsByMemberId(
		@RequestHeader(value = "Member-Id", required = false) Long memberId,
		@RequestParam int page,
		@RequestParam int size);

	@GetMapping("/books/{bookId}/reviews/count")
	ApiResponse<Long> countReviewsByBookId(@PathVariable long bookId);

	@GetMapping("/books/{bookId}/reviews/avg")
	ApiResponse<Double> avgReviewsByBookId(@PathVariable long bookId);
}
