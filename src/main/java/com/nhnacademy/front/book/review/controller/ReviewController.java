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
    public String createReview(@PathVariable long purchaseBookId) {
        return "review/create-review";
    }

    @PostMapping(value = "/{purchaseBookId}/review", consumes = "multipart/form-data")
    public String createReview(@PathVariable long purchaseBookId, @RequestHeader(value = "Member-id", required = false) Long memberId,
                               UserCreateReviewRequest reviewRequest) {
        log.info(reviewRequest.toString());
        reviewService.createReview(purchaseBookId, memberId, reviewRequest);
        return "review/create-review";
    }

    @GetMapping("/review/{reviewId}")
    public String readReviewDetail(@PathVariable long reviewId, Model model) {
        reviewService.readReviewDetail(reviewId);
        model.addAttribute("review", reviewService.readReviewDetail(reviewId));
        return "review/review-detail";
    }
}
