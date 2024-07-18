package com.nhnacademy.front.book.comment.controller.fegin;

import com.nhnacademy.front.book.comment.dto.request.CreateCommentRequest;
import com.nhnacademy.front.book.comment.dto.response.CommentResponse;
import com.nhnacademy.front.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "CommentClient", url = "http://${feign.client.url}/bookstore")
public interface CommentClient {
    @PostMapping("/books/reviews/{reviewId}")
    ApiResponse<Void> createComment(@PathVariable Long reviewId, @RequestHeader("Member-id") Long memberId, @RequestBody CreateCommentRequest createCommentRequest);

    @GetMapping("/books/reviews/{reviewId}/comments")
    ApiResponse<Page<CommentResponse>> readAllCommentsByReviewId(@PathVariable Long reviewId, @RequestParam int page, @RequestParam int size);

    @GetMapping("/books/reviews/member/comments")
    ApiResponse<Page<CommentResponse>> readAllCommentsByMemberId(@RequestHeader(value = "Member-Id", required = false) Long memberId, @RequestParam int page, @RequestParam int size);

    @DeleteMapping("/books/reviews/{commentId}/delete")
    ApiResponse<Void> deleteComment(@PathVariable Long commentId, @RequestHeader(value = "Member-Id", required = false) Long memberId);
}
