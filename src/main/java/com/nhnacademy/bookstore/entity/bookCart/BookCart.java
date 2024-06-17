package com.nhnacademy.bookstore.entity.bookCart;

import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.cart.Cart;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Set;

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
    @ManyToOne
    private Book book;

    @ManyToOne
    private Cart cart;

    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
    }
}
