package com.nhnacademy.front.book.comment.service;

import com.nhnacademy.front.book.comment.dto.request.CreateCommentRequest;
import com.nhnacademy.front.book.comment.dto.response.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    void createComment(Long reviewId, Long memberId, CreateCommentRequest createCommentRequest);

    Page<CommentResponse> readAllCommentsByReviewId(Long reviewId, int page, int size);

    Page<CommentResponse> readAllCommentsByMemberId(Long memberId, int page, int size);

    void deleteComment(Long commentId, Long memberId);
}
