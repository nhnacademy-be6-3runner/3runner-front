package com.nhnacademy.bookstore.purchase.purchase.repository;

import com.nhnacademy.bookstore.entity.purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
