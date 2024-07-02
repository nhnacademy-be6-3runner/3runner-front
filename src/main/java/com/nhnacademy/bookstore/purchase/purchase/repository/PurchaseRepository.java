package com.nhnacademy.bookstore.purchase.purchase.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.entity.purchase.Purchase;

import jakarta.validation.constraints.NotNull;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Boolean existsPurchaseByOrderNumber(@NotNull UUID orderNumber);

    List<Purchase> findByMember(Member member);

    Optional<Purchase> findPurchaseByOrderNumber(@NotNull UUID orderNumber);
}
