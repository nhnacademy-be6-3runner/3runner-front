package com.nhnacademy.front.book.comment.service.impl;

import com.nhnacademy.front.book.book.exception.InvalidApiResponseException;
import com.nhnacademy.front.book.comment.controller.fegin.CommentClient;
import com.nhnacademy.front.book.comment.dto.request.CreateCommentRequest;
import com.nhnacademy.front.book.comment.dto.response.CommentResponse;
import com.nhnacademy.front.book.comment.service.CommentService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentClient commentClient;

    @Override
    public void createComment(Long reviewId, Long memberId, CreateCommentRequest createCommentRequest) {
        log.info("createComment : {}", createCommentRequest);
        commentClient.createComment(reviewId, memberId, createCommentRequest);
    }

    @Override
    public Page<CommentResponse> readAllCommentsByReviewId(Long reviewId, int page, int size) {
        ApiResponse<Page<CommentResponse>> response = commentClient.readAllCommentsByReviewId(reviewId, page, size);

        if (response.getHeader().isSuccessful() && response.getBody() != null) {
            return response.getBody().getData();
        } else {
            throw new InvalidApiResponseException("댓글 페이지 조회 exception");
        }
    }

    @Override
    public Page<CommentResponse> readAllCommentsByMemberId(Long memberId, int page, int size) {
        ApiResponse<Page<CommentResponse>> response = commentClient.readAllCommentsByMemberId(memberId, page, size);

        if (response.getHeader().isSuccessful() && response.getBody() != null) {
            return response.getBody().getData();
        } else {
            throw new InvalidApiResponseException("사용자 댓글 조회 중 exception");
        }
    }

    @Override
    public void deleteComment(Long commentId, Long memberId) {
        commentClient.deleteComment(commentId, memberId);
    }
}
