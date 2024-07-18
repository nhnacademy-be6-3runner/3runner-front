package com.nhnacademy.front.book.review.controller;

import com.nhnacademy.front.book.review.dto.request.DeleteReviewRequest;
import com.nhnacademy.front.book.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewAdminController {
    private final ReviewService reviewService;

    @GetMapping("/admin/review/management")
    public String management() {
        return "admin/admin_review";
    }

    @PutMapping("/admin/review/management/{reviewId}/delete")
    public String deleteReview(@PathVariable long reviewId, @RequestHeader(value = "Member-Id", required = false) Long memberId, @RequestBody DeleteReviewRequest request) {
        log.info("Delete reason: {}", request.deletedReason());
        reviewService.deleteReview(reviewId, memberId, request);
        return "admin/admin_review";
    }
}
