package com.nhnacademy.front.book.review.service.impl;

import com.nhnacademy.front.book.review.controller.feign.ReviewClient;
import com.nhnacademy.front.book.review.dto.request.CreateReviewRequest;
import com.nhnacademy.front.book.review.dto.request.UserCreateReviewRequest;
import com.nhnacademy.front.book.review.service.ReviewService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewClient reviewClient;

    @Override
    public Long createReview(long purchaseBookId, long memberId, UserCreateReviewRequest request, String imageName) {
        CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
                .title(request.title())
                .content(request.content())
                .imageList(contentToImageList(request.content()))
                .ratings(request.ratings())
                .imageName(imageName).build();
        ApiResponse<Long> response = reviewClient.createReview(purchaseBookId, memberId, createReviewRequest);
        return response.getBody().getData();
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
