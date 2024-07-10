package com.nhnacademy.front.book.comment.controller;

import com.nhnacademy.front.book.comment.dto.request.CreateCommentRequest;
import com.nhnacademy.front.book.comment.service.CommentService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
