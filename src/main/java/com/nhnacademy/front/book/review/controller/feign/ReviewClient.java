package com.nhnacademy.front.book.review.controller.feign;

import com.nhnacademy.front.book.review.dto.request.CreateReviewRequest;
import com.nhnacademy.front.book.review.dto.response.ReviewDetailResponse;
import com.nhnacademy.front.book.review.dto.response.ReviewListResponse;
import com.nhnacademy.front.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ReviewClient", url = "http://${feign.client.url}/bookstore")
public interface ReviewClient {
    @PostMapping("/{purchaseBookId}/create")
    ApiResponse<Long> createReview(@PathVariable long purchaseBookId,
                                   @RequestHeader(value = "Member-id", required = false) Long memberId,
                                   @Valid @RequestBody CreateReviewRequest createReviewRequest);

    @GetMapping("reviews/{reviewId}")
    ApiResponse<ReviewDetailResponse> readReviewDetail(@PathVariable long reviewId);

    // TODO 리뷰 수정


    // TODO 리뷰 삭제

    // TODO 리뷰 전체 조회

    @GetMapping("/books/{bookId}/reviews")
    ApiResponse<Page<ReviewListResponse>> readAllReviewsByBookId(
            @PathVariable long bookId,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sort
    );

    // TODO 사용자 아이디로 리뷰 조회
}
