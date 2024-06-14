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
import java.util.HashSet;
import java.util.Set;

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
    @Size(min = 1, max = 200)
    private String content;

    @NotNull
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
    private Set<ReviewImage> reviewImageSet  = new HashSet<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> commentSet  = new HashSet<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReviewLike> reviewLikeSet  = new HashSet<>();

}
