package com.nhnacademy.bookstore.book.book.dto.elasticSearch;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.ZonedDateTime;

public class BookDocument {
    @Id
    private long id;
    private String title;
    private String description;
    private ZonedDateTime publishedDate;
    private int price;
    private int quantity;
    private int sellingPrice;
    private int view_count;
    private boolean packing;
    private String author;
    private String isbn;
    private String publisher;
    private ZonedDateTime createdAt;
}
