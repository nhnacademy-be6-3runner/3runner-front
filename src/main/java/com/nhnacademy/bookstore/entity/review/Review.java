package com.nhnacademy.bookstore.entity.review;

import com.nhnacademy.bookstore.entity.comment.Comment;
import com.nhnacademy.bookstore.entity.purchaseBook.PurchaseBook;
import com.nhnacademy.bookstore.entity.review.enums.ReviewStatus;
import com.nhnacademy.bookstore.entity.reviewImage.ReviewImage;
import com.nhnacademy.bookstore.entity.reviewLike.ReviewLike;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private PurchaseBook purchaseBook;

    @NotNull
    @Size(min = 1, max = 50)
    @Setter
    private String title;

    @NotNull
    @Size(min = 1, max = 200)
    @Setter
    private String content;

    @NotNull
    @Setter
    private double rating;

    @NotNull
    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private ZonedDateTime deletedAt;

    private ReviewStatus reviewStatus;

    @Size(min = 1, max = 100)
    private String deletedReason;

    // 연결

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> reviewImageList = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewLike> reviewLikeList = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }

    /**
     * 리뷰가 삭제되면 자동으로 삭제 시간 업데이트
     *
     * @param reviewStatus 리뷰 상태
     */
    public void setReviewStatus(ReviewStatus reviewStatus) {
        if (this.reviewStatus != reviewStatus && reviewStatus == ReviewStatus.DELETE) {
            this.deletedAt = ZonedDateTime.now();
        }
        this.reviewStatus = reviewStatus;
    }


}
