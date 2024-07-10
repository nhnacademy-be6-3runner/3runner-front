package com.nhnacademy.front.book.reviewLike.controller.feign;

import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ReviewLikeClient", url = "http://${feign.client.url}/bookstore/books/review")
public interface ReviewLikeClient {
    @PostMapping("/{reviewId}/like")
    ApiResponse<Void> createReviewLike(@PathVariable("reviewId") Long reviewId,
                                       @RequestHeader(value = "Member-Id", required = false) Long memberId);
}
