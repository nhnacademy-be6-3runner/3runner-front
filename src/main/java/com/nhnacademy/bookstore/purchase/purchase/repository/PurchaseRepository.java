package com.nhnacademy.bookstore.purchase.purchase.repository;

import com.nhnacademy.bookstore.entity.purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
