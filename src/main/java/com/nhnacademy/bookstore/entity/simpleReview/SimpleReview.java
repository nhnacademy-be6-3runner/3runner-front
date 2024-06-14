package com.nhnacademy.bookstore.entity.simpleReview;

import com.nhnacademy.bookstore.entity.purchaseBook.PurchaseBook;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.ZonedDateTime;

@Entity
public class SimpleReview {

    @Id
    private long purchaseBookId;

    @OneToOne
    @MapsId
    private PurchaseBook purchaseBook;

    @NotNull
    @Size(min = 1, max = 100)
    private String content;

    @NotNull
    ZonedDateTime createdAt;

    ZonedDateTime updatedAt;
}
