package com.nhnacademy.bookstore.purchase.purchaseBook.repository;

import com.nhnacademy.bookstore.entity.purchaseBook.PurchaseBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


/**
 * purchaseBook repository
 * @author 정주혁
 */
public interface PurchaseBookRepository extends JpaRepository<PurchaseBook, Long> {
    List<PurchaseBook> findAllByPurchaseId(Long purchaseId);
    Optional<PurchaseBook> findByPurchaseIdAndBookId(Long purchaseId, Long bookId);
}
