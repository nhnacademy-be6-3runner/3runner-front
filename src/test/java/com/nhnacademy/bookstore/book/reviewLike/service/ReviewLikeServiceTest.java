package com.nhnacademy.bookstore.book.reviewLike.service;

import com.nhnacademy.bookstore.book.bookLike.exception.CannotLikeOwnReviewLikeException;
import com.nhnacademy.bookstore.book.review.repository.ReviewRepository;
import com.nhnacademy.bookstore.book.reviewLike.exception.ReviewLikeAlreadyExistsException;
import com.nhnacademy.bookstore.book.reviewLike.repository.ReviewLikeRepository;
import com.nhnacademy.bookstore.book.reviewLike.service.impl.ReviewLikeServiceImpl;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.entity.purchase.Purchase;
import com.nhnacademy.bookstore.entity.purchase.enums.MemberType;
import com.nhnacademy.bookstore.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.bookstore.entity.purchaseBook.PurchaseBook;
import com.nhnacademy.bookstore.entity.review.Review;
import com.nhnacademy.bookstore.entity.reviewLike.ReviewLike;
import com.nhnacademy.bookstore.member.member.dto.request.CreateMemberRequest;
import com.nhnacademy.bookstore.member.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReviewLikeServiceTest {
    @Mock
    private ReviewLikeRepository reviewLikeRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private ReviewLikeServiceImpl reviewLikeService;

    private Member member;
    private Member member2;
    private Review review;

    @BeforeEach
    public void setUp() {
        member = new Member(CreateMemberRequest.builder()
                .password("12345677")
                .name("1")
                .age(1)
                .phone("1")
                .birthday(ZonedDateTime.now().toString())
                .email("dfdaf@nav.com")
                .build());
        member.setId(1L);

        member2 = new Member(CreateMemberRequest.builder()
                .password("12345677")
                .name("1")
                .age(1)
                .phone("1")
                .birthday(ZonedDateTime.now().toString())
                .email("dfdaf2@nav.com")
                .build());
        member2.setId(2L);

        Book book = new Book(
                "책1",
                "책1입니다.",
                ZonedDateTime.now(),
                1000,
                10,
                900,
                0,
                true,
                "작가",
                "123456789",
                "출판사",
                null,
                null,
                null
        );

        Purchase purchase = new Purchase(
                UUID.randomUUID(),
                PurchaseStatus.CONFIRMATION,
                100,
                10,
                ZonedDateTime.now(),
                "road",
                "password",
                ZonedDateTime.now(),
                true,
                MemberType.MEMBER,
                member);

        PurchaseBook purchaseBook = new PurchaseBook(book, 1, 100, purchase);

        review = new Review(
                purchaseBook,
                "리뷰입니다.",
                "아주 추천합니다. 좋은 책입니다.",
                4.5,
                null,
                null,
                null
        );

    }

    @DisplayName("리뷰 좋아요 생성 테스트")
    @Test
    void createReviewLikeTest() {
        given(reviewRepository.findById(1L)).willReturn(Optional.of(review));
        given(memberRepository.findById(2L)).willReturn(Optional.of(member2));
        given(reviewLikeRepository.existsByReviewAndMember(review, member2)).willReturn(false);

        reviewLikeService.createReviewLike(1L, 2L);

        verify(reviewRepository).save(review);
    }

    @DisplayName("자신의 리뷰에 좋아요 생성 시도시 예외 발생 테스트")
    @Test
    void createReviewLikeOwnReviewTest() {
        given(reviewRepository.findById(1L)).willReturn(Optional.of(review));
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));

        assertThrows(CannotLikeOwnReviewLikeException.class, () -> reviewLikeService.createReviewLike(1L, 1L));
    }

    @DisplayName("이미 좋아요가 존재할 때 예외 발생 테스트")
    @Test
    void createReviewLikeAlreadyExistsTest() {
        given(reviewRepository.findById(1L)).willReturn(Optional.of(review));
        given(memberRepository.findById(2L)).willReturn(Optional.of(member2));
        given(reviewLikeRepository.existsByReviewAndMember(review, member2)).willReturn(true);

        assertThrows(ReviewLikeAlreadyExistsException.class, () -> reviewLikeService.createReviewLike(1L, 2L));
    }

    @DisplayName("리뷰 좋아요 삭제 테스트")
    @Test
    void deleteReviewLikeTest() {
        ReviewLike reviewLike = ReviewLike.createReviewLike(member2, review);
        given(reviewLikeRepository.findById(1L)).willReturn(Optional.of(reviewLike));
        reviewLikeService.deleteReviewLike(1L, 2L);

        verify(reviewLikeRepository).delete(reviewLike);
    }

    @DisplayName("리뷰 좋아요 카운트 테스트")
    @Test
    void countReviewLikeTest() {
        given(reviewLikeRepository.countByReviewId(1L)).willReturn(1L);

        Long count = reviewLikeService.countReviewLike(1L);

        assertThat(count).isEqualTo(1L);
    }
}
