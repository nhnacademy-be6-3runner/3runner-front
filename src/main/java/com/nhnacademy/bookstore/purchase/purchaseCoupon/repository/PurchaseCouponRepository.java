package com.nhnacademy.bookstore.purchase.purchaseCoupon.repository;

import com.nhnacademy.bookstore.entity.purchase.Purchase;
import com.nhnacademy.bookstore.entity.purchaseCoupon.PurchaseCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PurchaseCouponRepository extends JpaRepository<PurchaseCoupon, Long> {
    List<PurchaseCoupon> findAllByPurchase(Purchase purchase);
}
