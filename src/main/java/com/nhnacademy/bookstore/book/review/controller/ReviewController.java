package com.nhnacademy.bookstore.book.review.controller;


import com.nhnacademy.bookstore.book.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstore.book.review.dto.response.ReviewListResponse;
import com.nhnacademy.bookstore.book.review.dto.response.UserReadReviewResponse;
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
// TODO requestMapping 은 수정할 예정입니다.
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
    @PostMapping("/reviews/create")
    public ApiResponse<Void> createReview(@RequestParam long purchaseId, @RequestHeader("Member-Id") Long memberId, @Valid @RequestBody CreateReviewRequest createReviewRequest,
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
     * 리뷰 상세 조회입니다.
     *
     * @param reviewId 조회할 리뷰 아이디
     * @return ApiResponse<reviewDetailResponse>
     */
    @GetMapping("reviews/{reviewId}")
    public ApiResponse<UserReadReviewResponse> readReviewDetail(@PathVariable long reviewId) {
        UserReadReviewResponse userReadReviewResponse = reviewService.readDetailUserReview(reviewId);

        return ApiResponse.success(userReadReviewResponse);
    }

    /**
     * 책 아이디로 리뷰 전체 조회 메서드입니다.
     *
     * @param bookId 책 아이디
     * @param page   페이지
     * @param size   사이즈
     * @return ApiResponse<reviewListResponse>
     */
    @GetMapping("/{bookId}/reviews")
    public ApiResponse<Page<ReviewListResponse>> readReviewsByBookId(@PathVariable long bookId, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReviewListResponse> reviewList = reviewService.readAllReviewsByBookId(bookId, pageable);
        return new ApiResponse<>(
                new ApiResponse.Header(true, 200),
                new ApiResponse.Body<>(reviewList)
        );
    }

    /**
     * 사용자 아이디로 리뷰 전체 조회 메서드입니다.
     *
     * @param memberId 멤버 아이디
     * @param page     페이지
     * @param size     사이즈
     * @return ApiResponse<reviewListResponse>
     */
    @GetMapping("/reviews/member")
    public ApiResponse<Page<ReviewListResponse>> readReviewsByMemberId(@RequestHeader("Member-Id") Long memberId, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReviewListResponse> reviewList = reviewService.readAllReviewsByMemberId(memberId, pageable);
        return new ApiResponse<>(
                new ApiResponse.Header(true, 200),
                new ApiResponse.Body<>(reviewList)
        );
    }
}
