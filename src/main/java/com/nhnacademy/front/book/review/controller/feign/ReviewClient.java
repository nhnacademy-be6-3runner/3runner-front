package com.nhnacademy.front.book.review.controller.feign;

import com.nhnacademy.front.book.review.dto.request.CreateReviewRequest;
import com.nhnacademy.front.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ReviewClient", url = "http://${feign.client.url}/bookstore")
public interface ReviewClient {
    // TODO 리뷰 생성
    @PostMapping("/{purchaseBookId}/create")
    ApiResponse<Long> createReview(@PathVariable long purchaseBookId,
                                   @RequestHeader(value = "Member-id", required = false) Long memberId,
                                   @Valid @RequestBody CreateReviewRequest createReviewRequest);

    // TODO 리뷰 수정


    // TODO 리뷰 삭제

    // TODO 리뷰 전체 조회

    // TODO 책 아이디로 리뷰 조회

    // TODO 사용자 아이디로 리뷰 조회
}
