package com.nhnacademy.bookstore.entity.book;

import com.nhnacademy.bookstore.entity.bookCategory.BookCategory;
import com.nhnacademy.bookstore.entity.bookImage.BookImage;
import com.nhnacademy.bookstore.entity.bookTag.BookTag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 1, max = 50)
    @NotNull
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;


    private ZonedDateTime publishedDate;

    @NotNull
    @Min(0)
    private int price;

    @NotNull
    @Min(0)
    private int quantity;

    @NotNull
    @Min(0)
    private int sellingPrice;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "int default 0")
    private int view_count;

//    @Column(nullable = false, columnDefinition = "bit(1) default 1")
    @NotNull
    private boolean packing;

    @NotNull
    @Size(min = 1, max = 50)
    private String author;

    @NotNull
    @Size(min = 1, max = 20)
    private String isbn;

    @NotNull
    @Size(min = 1, max = 50)
    private String publisher;

    @NotNull
    private ZonedDateTime createdAt;

//    public Book(long id, String title, String description, ZonedDateTime publishedDate, )
    //연결

    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookCategory> bookCategorySet = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookTag> bookTagSet = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookImage> bookImageSet = new HashSet<>();



    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
    }

    public Book(String title, String description, ZonedDateTime publishedDate, int price, int quantity, int sellingPrice, int view_count, boolean packing, String author, String isbn, String publisher,  Set<BookCategory> bookCategorySet, Set<BookTag> bookTagSet, Set<BookImage> bookImageSet) {
        this.title = title;
        this.description = description;
        this.publishedDate = publishedDate;
        this.price = price;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.view_count = view_count;
        this.packing = packing;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.bookCategorySet = bookCategorySet;
        this.bookTagSet = bookTagSet;
        this.bookImageSet = bookImageSet;
    }
}
