package com.nhnacademy.bookstore.entity.purchaseBook;

import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.purchase.Purchase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class PurchaseBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Book book;


    @NotNull
    @Min(0)
    private int quantity;

    @NotNull
    @Min(0)
    private int price;

    @ManyToOne
    private Purchase purchase;

}
