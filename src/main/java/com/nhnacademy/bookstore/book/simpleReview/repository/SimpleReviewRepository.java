package com.nhnacademy.bookstore.book.simpleReview.repository;

import com.nhnacademy.bookstore.entity.simpleReview.SimpleReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleReviewRepository extends JpaRepository<SimpleReview, Long> {
}
