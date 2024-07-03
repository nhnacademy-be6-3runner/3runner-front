package com.nhnacademy.front.book.review.controller;

import com.nhnacademy.front.book.image.service.ImageService;
import com.nhnacademy.front.book.review.dto.request.CreateReviewRequest;
import com.nhnacademy.front.book.review.dto.request.UserCreateReviewRequest;
import com.nhnacademy.front.book.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ImageService imageService;

    @GetMapping("/{purchaseBookId}/review")
    public String createReview(@PathVariable long purchaseBookId) {
        return "/review/create-review";
    }

    @PostMapping(value = "/{purchaseBookId}/review", consumes = "multipart/form-data")
    public String createReview(@PathVariable long purchaseBookId, @RequestHeader(value = "Member-id", required = false) Long memberId,
                               UserCreateReviewRequest reviewRequest, Model model) {
        String imageName = imageService.upload(reviewRequest.image(), "review");
        reviewService.createReview(purchaseBookId,memberId,reviewRequest, imageName);
        return "/review/create-review";
    }


}
