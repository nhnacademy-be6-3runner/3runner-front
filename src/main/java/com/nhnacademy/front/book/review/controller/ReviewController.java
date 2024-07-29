package com.nhnacademy.front.book.review.controller;

import com.nhnacademy.front.book.review.dto.request.UserCreateReviewRequest;
import com.nhnacademy.front.book.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;

	@GetMapping("/{purchaseBookId}/review")
	public String createReview() {
		return "review/create-review";
	}

	@GetMapping("/review/{reviewId}")
	public String readReviewDetail(@PathVariable long reviewId, Model model) {
		reviewService.readReviewDetail(reviewId);
		model.addAttribute("review", reviewService.readReviewDetail(reviewId));
		return "review/review-detail";
	}

	@GetMapping("/review/edit/{reviewId}")
	public String editReview(@PathVariable long reviewId, Model model) {
		model.addAttribute("review", reviewService.readReviewDetail(reviewId));
		return "review/edit-review";
	}

	@PutMapping("/review/edit/{reviewId}")
	public String updateReview(@PathVariable long reviewId,
		@RequestHeader(value = "Member-Id", required = false) Long memberId, UserCreateReviewRequest reviewRequest) {
		log.info(reviewRequest.toString());
		reviewService.updateReview(reviewId, memberId, reviewRequest);
		return "redirect:/review/" + reviewId;
	}
}
