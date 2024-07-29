package com.nhnacademy.front.book.reviewLike.controller.feign;

import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ReviewLikeClient", url = "${feign.client.url}/bookstore/books/review")
public interface ReviewLikeClient {
	@PostMapping("/{reviewId}/like")
	ApiResponse<Void> createReviewLike(@PathVariable("reviewId") Long reviewId,
		@RequestHeader(value = "Member-Id", required = false) Long memberId);

	@DeleteMapping("/{reviewId}/like")
	ApiResponse<Void> deleteReviewLike(@PathVariable("reviewId") Long reviewId,
		@RequestHeader(value = "Member-Id", required = false) Long memberId);

	@GetMapping("/{reviewId}/like/count")
	ApiResponse<Long> countReviewLike(@PathVariable("reviewId") Long reviewId);

	@GetMapping("/{reviewId}/like/status")
	ApiResponse<Boolean> isReviewLikedByMember(@PathVariable("reviewId") Long reviewId,
		@RequestHeader(value = "Member-Id", required = false) Long memberId);
}
