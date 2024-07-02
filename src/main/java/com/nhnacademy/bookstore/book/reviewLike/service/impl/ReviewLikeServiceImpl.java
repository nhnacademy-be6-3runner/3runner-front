package com.nhnacademy.bookstore.book.reviewLike.service.impl;

import com.nhnacademy.bookstore.book.bookLike.exception.CannotLikeOwnReviewLikeException;
import com.nhnacademy.bookstore.book.review.exception.ReviewNotExistsException;
import com.nhnacademy.bookstore.book.review.repository.ReviewRepository;
import com.nhnacademy.bookstore.book.reviewLike.exception.DeleteReviewLikeException;
import com.nhnacademy.bookstore.book.reviewLike.exception.ReviewLikeAlreadyExistsException;
import com.nhnacademy.bookstore.book.reviewLike.exception.ReviewLikeNotExistsException;
import com.nhnacademy.bookstore.book.reviewLike.repository.ReviewLikeRepository;
import com.nhnacademy.bookstore.book.reviewLike.service.ReviewLikeService;
import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.entity.review.Review;
import com.nhnacademy.bookstore.entity.reviewLike.ReviewLike;
import com.nhnacademy.bookstore.member.member.exception.MemberNotExistsException;
import com.nhnacademy.bookstore.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 리뷰 좋아요 기능을 위한 서비스 구현체입니다.
 *
 * @author 김은비
 */
@Service
@RequiredArgsConstructor
public class ReviewLikeServiceImpl implements ReviewLikeService {
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    /**
     * 리뷰 좋아요 생성 메서드입니다.
     *
     * @param reviewId 리뷰 아이디
     * @param memberId 멤버 아이디
     */
    @Override
    @Transactional
    public void createReviewLike(long reviewId, long memberId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotExistsException::new);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotExistsException::new);

        if (review.getPurchaseBook().getPurchase().getMember().getId().equals(memberId)) {
            throw new CannotLikeOwnReviewLikeException();
        }

        if (reviewLikeRepository.existsByReviewAndMember(review, member)) {
            throw new ReviewLikeAlreadyExistsException();
        }

        ReviewLike reviewLike = new ReviewLike(member, review);
        review.addReviewLike(reviewLike);
        reviewRepository.save(review);
    }

    /**
     * 리뷰 좋아요 삭제 메서드입니다.
     *
     * @param reviewLikeId 리뷰 좋아요 아이디
     * @param memberId     멤버 아이디
     */
    @Override
    @Transactional
    public void deleteReviewLike(long reviewLikeId, long memberId) {
        ReviewLike reviewLike = reviewLikeRepository.findById(reviewLikeId)
                .orElseThrow(ReviewLikeNotExistsException::new);
        if (!reviewLike.getMember().getId().equals(memberId)) {
            throw new DeleteReviewLikeException();
        }
        reviewLikeRepository.delete(reviewLike);
    }

    /**
     * 리뷰에 대한 좋아요 카운트 메서드입니다.
     *
     * @param reviewId 리뷰 아이디
     * @return 좋아요 갯수
     */
    @Override
    @Transactional(readOnly = true)
    public Long countReviewLike(long reviewId) {
        return reviewLikeRepository.countByReviewId(reviewId);
    }
}
