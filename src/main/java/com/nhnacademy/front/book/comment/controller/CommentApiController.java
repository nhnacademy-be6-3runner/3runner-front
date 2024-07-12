package com.nhnacademy.front.book.comment.controller;

import com.nhnacademy.front.book.comment.dto.request.CreateCommentRequest;
import com.nhnacademy.front.book.comment.dto.response.CommentResponse;
import com.nhnacademy.front.book.comment.service.CommentService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping("/api/review/{reviewId}/comments")
    public ApiResponse<Void> createComment(@PathVariable Long reviewId,
                                           @RequestHeader(value = "Member-Id", required = false) Long memberId,
                                           CreateCommentRequest commentRequest) {
        log.info("comment : {}", commentRequest);
        commentService.createComment(reviewId, memberId, commentRequest);
        return new ApiResponse<>(new ApiResponse.Header(true, 201));
    }

    @GetMapping("/api/review/{reviewId}/comments")
    public ApiResponse<Page<CommentResponse>> readAllCommentsByReviewId(@PathVariable Long reviewId,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        Page<CommentResponse> responses = commentService.readAllCommentsByReviewId(reviewId, page, size);
        return ApiResponse.success(responses);
    }

    @GetMapping("/api/mypage/comments")
    public ApiResponse<Page<CommentResponse>> readAllCommentsByMemberId(@RequestHeader(value = "Member-Id", required = false) Long memberId,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        Page<CommentResponse> responses = commentService.readAllCommentsByMemberId(memberId, page, size);
        return ApiResponse.success(responses);
    }

    @DeleteMapping("/api/mypage/comment/{commentId}/delete")
    public ApiResponse<Void> deleteComment(@PathVariable long commentId, @RequestHeader(value = "Member-Id", required = false) Long memberId) {
        commentService.deleteComment(commentId, memberId);
        return new ApiResponse<>(new ApiResponse.Header(true, 200));
    }
}
