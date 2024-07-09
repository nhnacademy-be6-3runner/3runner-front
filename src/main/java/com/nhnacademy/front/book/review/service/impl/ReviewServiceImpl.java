package com.nhnacademy.front.book.review.service.impl;

import com.nhnacademy.front.book.book.exception.InvalidApiResponseException;
import com.nhnacademy.front.book.review.controller.feign.ReviewClient;
import com.nhnacademy.front.book.review.dto.request.CreateReviewRequest;
import com.nhnacademy.front.book.review.dto.request.UserCreateReviewRequest;
import com.nhnacademy.front.book.review.dto.response.ReviewDetailResponse;
import com.nhnacademy.front.book.review.dto.response.ReviewListResponse;
import com.nhnacademy.front.book.review.service.ReviewService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewClient reviewClient;

    @Override
    public Long createReview(long purchaseBookId, Long memberId, UserCreateReviewRequest request) {
        CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
                .title(request.title())
                .content(request.content())
                .imageList(contentToImageList(request.content()))
                .ratings(request.ratings())
                .build();
        log.info("리뷰 생성 : {}", createReviewRequest);
        ApiResponse<Long> response = reviewClient.createReview(purchaseBookId, memberId, createReviewRequest);
        return response.getBody().getData();
    }

    @Override
    public Page<ReviewListResponse> readAllReviewsByBookId(Long bookId, int page, int size, String sort) {
        ApiResponse<Page<ReviewListResponse>> response = reviewClient.readAllReviewsByBookId(bookId, page, size, sort);

        if (response.getHeader().isSuccessful() && response.getBody() != null) {
            return response.getBody().getData();
        } else {
            throw new InvalidApiResponseException("리뷰 페이지 조회 exception");
        }
    }

    @Override
    public ReviewDetailResponse readReviewDetail(long reviewId) {
        ApiResponse<ReviewDetailResponse> response = reviewClient.readReviewDetail(reviewId);
        if (response.getHeader().isSuccessful() && response.getBody() != null) {
            return response.getBody().getData();
        } else {
            throw new InvalidApiResponseException("리뷰 상세 조회 exception");
        }
    }

    private List<String> contentToImageList(String content) {
        List<String> imageList = new ArrayList<>();
        String[] split = content.split("fileName=");
        if (split.length > 1) {
            for (int i = 1; i < split.length; i++) {
                imageList.add(split[i].substring(0, split[i].indexOf('"')));
            }
        }
        return imageList;
    }

}
