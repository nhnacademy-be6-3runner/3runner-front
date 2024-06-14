package com.nhnacademy.bookstore.purchase.coupon.repository;

import com.nhnacademy.bookstore.entity.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
