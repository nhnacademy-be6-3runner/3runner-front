package com.nhnacademy.bookstore.book.reviewLike.repository;

import com.nhnacademy.bookstore.entity.reviewLike.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
}
