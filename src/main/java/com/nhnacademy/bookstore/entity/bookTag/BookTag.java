package com.nhnacademy.bookstore.entity.bookTag;

import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.tag.Tag;
import jakarta.persistence.*;

@Entity
public class BookTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    @ManyToOne
    private Book book;

    @ManyToOne
    private Tag tag;
}
