package com.nhnacademy.bookstore.entity.bookCategory;

import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Category category;

    public static BookCategory create(Book book, Category category) {
        return BookCategory.builder()
                .book(book)
                .category(category)
                .build();
    }
}
