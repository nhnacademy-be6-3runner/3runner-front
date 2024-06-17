package com.nhnacademy.bookstore.purchase.bookCart.repository;

import com.nhnacademy.bookstore.entity.bookCart.BookCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCartRepository extends JpaRepository<BookCart, Long> {
}
