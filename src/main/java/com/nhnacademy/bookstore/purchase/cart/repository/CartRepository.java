package com.nhnacademy.bookstore.purchase.cart.repository;


import com.nhnacademy.bookstore.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
