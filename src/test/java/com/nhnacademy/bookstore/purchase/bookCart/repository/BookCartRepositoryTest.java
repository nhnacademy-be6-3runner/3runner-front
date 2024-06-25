package com.nhnacademy.bookstore.purchase.bookCart.repository;

import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.bookCart.BookCart;
import com.nhnacademy.bookstore.entity.cart.Cart;
import com.nhnacademy.bookstore.entity.member.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class BookCartRepositoryTest {

    @Autowired
    private BookCartRepository bookCartRepository;

    @Autowired
    private EntityManager entityManager;

    private Book book;
    private Cart cart;
    private Member member;
    private BookCart bookCart;

    @BeforeEach
    void setUp() {
        book = new Book(
                "Test Title",
                "Test Description",
                ZonedDateTime.now(),
                1000,
                10,
                900,
                0,
                true,
                "Test Author",
                "123456789",
                "Test Publisher",
                null,
                null,
                null
        );
        entityManager.persist(book);

        cart = new Cart();
        entityManager.persist(cart);

        bookCart = new BookCart();
        bookCart.setBook(book);
        bookCart.setCart(cart);
        bookCart.setQuantity(1);
        bookCart.setCreatedAt(ZonedDateTime.now());
        entityManager.persist(bookCart);
    }

    @Test
    void testFindByBookAndCart() {
        Optional<BookCart> found = bookCartRepository.findByBookAndCart(book, cart);

        assertThat(found).isPresent();
        assertThat(found.get().getBook()).isEqualTo(book);
        assertThat(found.get().getCart()).isEqualTo(cart);
    }

    @Test
    void testFindAllByCart() {
        List<BookCart> found = bookCartRepository.findAllByCart(cart);

        assertThat(found).isNotEmpty();
        assertThat(found.getFirst().getCart()).isEqualTo(cart);
    }

}