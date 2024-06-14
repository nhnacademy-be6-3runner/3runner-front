package com.nhnacademy.bookstore.purchase.purchaseBook.repository;

import com.nhnacademy.bookstore.entity.purchaseBook.PurchaseBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseBookRepository extends JpaRepository<PurchaseBook, Long> {
}
