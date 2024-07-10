package com.nhnacademy.front.book.comment.controller.fegin;

import com.nhnacademy.front.book.comment.dto.request.CreateCommentRequest;
import com.nhnacademy.front.book.comment.dto.response.CommentResponse;
import com.nhnacademy.front.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "CommentClient", url = "http://${feign.client.url}/bookstore/books/reviews")
public interface CommentClient {
    @PostMapping("/{reviewId}")
    ApiResponse<Void> createComment(@PathVariable Long reviewId, @RequestHeader("Member-id") Long memberId, @RequestBody CreateCommentRequest createCommentRequest);

    @GetMapping("/{reviewId}/comments")
    ApiResponse<CommentResponse> readAllCommentsByReviewId(@PathVariable Long reviewId, @RequestParam int page, @RequestParam int size);
}
