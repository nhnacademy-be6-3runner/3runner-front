package com.nhnacademy.bookstore.purchase.paymentType.repository;

import com.nhnacademy.bookstore.entity.paymentType.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
}
