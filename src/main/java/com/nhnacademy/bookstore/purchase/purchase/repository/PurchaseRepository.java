package com.nhnacademy.bookstore.purchase.purchase.repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.entity.purchase.Purchase;
import com.nhnacademy.bookstore.entity.purchase.enums.PurchaseStatus;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	Boolean existsPurchaseByOrderNumber(UUID orderNumber);

	List<Purchase> findByMember(Member member);

	Optional<Purchase> findPurchaseByOrderNumber(UUID orderNumber);

	List<Purchase> findByShippingDateBefore(ZonedDateTime shippingDate);
}
