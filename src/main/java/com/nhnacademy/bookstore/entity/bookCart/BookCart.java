package com.nhnacademy.bookstore.entity.bookCart;

import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.cart.Cart;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
public class BookCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Min(0)
    @Column(nullable = false, columnDefinition = "int default 0")
    private int quantity;

    @NotNull
    private ZonedDateTime createdAt;


    // 연결
    @ManyToOne(optional = false)
    private Book book;

    @ManyToOne(optional = false)
    private Cart cart;

    public BookCart(int quantity, ZonedDateTime createdAt, Book book, Cart cart) {
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.book = book;
        this.cart = cart;
    }
}
