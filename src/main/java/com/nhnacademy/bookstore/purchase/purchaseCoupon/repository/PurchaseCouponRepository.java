package com.nhnacademy.bookstore.purchase.purchaseCoupon.repository;

import com.nhnacademy.bookstore.entity.purchaseCoupon.PurchaseCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseCouponRepository extends JpaRepository<PurchaseCoupon, Long> {
}
