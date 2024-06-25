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
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
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
    private int viewCount;

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
    private List<BookCategory> bookCategoryList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookTag> bookTagList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookImage> bookImageList = new ArrayList<>();



    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
    }

    public Book(String title, String description, ZonedDateTime publishedDate, int price, int quantity, int sellingPrice, int viewCount, boolean packing, String author, String isbn, String publisher,  List<BookCategory> bookCategoryList, List<BookTag> bookTagList, List<BookImage> bookImageList) {
        this.title = title;
        this.description = description;
        this.publishedDate = publishedDate;
        this.price = price;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.viewCount = viewCount;
        this.packing = packing;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.bookCategoryList = bookCategoryList;
        this.bookTagList = bookTagList;
        this.bookImageList = bookImageList;
    }
}
