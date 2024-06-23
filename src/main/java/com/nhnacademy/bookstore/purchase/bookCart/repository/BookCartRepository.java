package com.nhnacademy.bookstore.purchase.bookCart.repository;

import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.bookCart.BookCart;
import com.nhnacademy.bookstore.entity.cart.Cart;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 도서장바구니 JPA 저장소.
 *
 * @author 김병우
 */
public interface BookCartRepository extends JpaRepository<BookCart, Long> {
    Optional<BookCart> findByBookAndCart(Book book, Cart cart);
    List<BookCart> findAllByCart(Cart cart);
}
