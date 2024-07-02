package com.nhnacademy.bookstore.book.review.controller;


import com.nhnacademy.bookstore.book.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstore.book.review.dto.response.ReviewDetailResponse;
import com.nhnacademy.bookstore.book.review.dto.response.ReviewListResponse;
import com.nhnacademy.bookstore.book.review.exception.CreateReviewRequestFormException;
import com.nhnacademy.bookstore.book.review.service.ReviewService;
import com.nhnacademy.bookstore.book.reviewImage.service.ReviewImageService;
import com.nhnacademy.bookstore.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 리뷰 컨트롤러입니다.
 *
 * @author 김은비
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookstore/books")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewImageService reviewImageService;

    /**
     * 리뷰 생성 요청입니다.
     *
     * @param purchaseId          주문-도서 아이디
     * @param memberId            사용자 아이디
     * @param createReviewRequest 리뷰 생성 요청 dto
     * @param bindingResult       데이터 바인딩 결과
     * @return ApiResponse<>
     */
    @PostMapping("/reviews")
    public ApiResponse<Void> createReview(@RequestParam long purchaseId, @RequestParam long memberId, @Valid @RequestBody CreateReviewRequest createReviewRequest,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CreateReviewRequestFormException(bindingResult.getFieldErrors().toString());
        }
        long reviewId = reviewService.createReview(purchaseId, memberId, createReviewRequest);
        reviewImageService.createReviewImage(createReviewRequest.imageList(), reviewId);
        return new ApiResponse<>(new ApiResponse.Header(true, 201));
    }

    /**
     * 리뷰 전체 조회입니다.
     *
     * @param page 페이지
     * @param size 사이즈
     * @return ApiResponse<reviewList>
     */
    @GetMapping("/reviews")
    public ApiResponse<Page<ReviewListResponse>> readAllReviews(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReviewListResponse> reviewList = reviewService.readAllReviews(pageable);
        return new ApiResponse<>(
                new ApiResponse.Header(true, 200),
                new ApiResponse.Body<>(reviewList)
        );
    }

    /**
     * 리뷰 싱세 조회입니다.
     *
     * @param reviewId 조회할 리뷰 아이디
     * @return ApiResponse<reviewDetailResponse>
     */
    @GetMapping("reviews/{reviewId}")
    public ApiResponse<ReviewDetailResponse> readReviewDetail(@PathVariable long reviewId) {
        ReviewDetailResponse reviewDetailResponse = reviewService.readDetailReview(reviewId);
        // TODO 리뷰 댓글 서비스 추가
        // TODO 리뷰 좋아요 서비스 추가
        return ApiResponse.success(reviewDetailResponse);
    }

    @GetMapping("/{bookId}/reviews")
    public ApiResponse<Page<ReviewListResponse>> readReviewsByBookId(@PathVariable long bookId, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReviewListResponse> reviewList = reviewService.readAllReviewsByBookId(bookId, pageable);
        return new ApiResponse<>(
                new ApiResponse.Header(true, 200),
                new ApiResponse.Body<>(reviewList)
        );
    }

    // TODO 사용자 아이디로 리뷰 조회
    // mypage 에서 진행?
}
