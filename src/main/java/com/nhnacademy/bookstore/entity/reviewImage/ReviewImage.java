package com.nhnacademy.bookstore.entity.reviewImage;

import com.nhnacademy.bookstore.entity.review.Review;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Review review;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(unique = true)
    private String url;
}