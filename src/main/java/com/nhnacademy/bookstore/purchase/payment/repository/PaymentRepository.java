package com.nhnacademy.bookstore.purchase.payment.repository;

import com.nhnacademy.bookstore.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
