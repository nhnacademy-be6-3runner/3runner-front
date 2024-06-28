package com.nhnacademy.bookstore.entity.review;

import com.nhnacademy.bookstore.entity.comment.Comment;
import com.nhnacademy.bookstore.entity.purchaseBook.PurchaseBook;
import com.nhnacademy.bookstore.entity.review.enums.ReviewStatus;
import com.nhnacademy.bookstore.entity.reviewImage.ReviewImage;
import com.nhnacademy.bookstore.entity.reviewLike.ReviewLike;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private PurchaseBook purchaseBook;

    @NotNull
    @Size(min = 1, max = 50)
    private String title;

    @NotNull
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    private double rating;

    @NotNull
    private boolean updated;

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
    protected void onUpdate() {
        this.updatedAt = ZonedDateTime.now();
        this.updated = true;
    }

    public void addComment(Comment comment) {
        this.commentList.add(comment);
        comment.setReview(this);
    }

    public void addReviewLike(ReviewLike reviewLike) {
        this.reviewLikeList.add(reviewLike);
        reviewLike.setReview(this);
    }
}
