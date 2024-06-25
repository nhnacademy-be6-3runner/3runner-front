package com.nhnacademy.bookstore.entity.book;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBook is a Querydsl query type for Book
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBook extends EntityPathBase<Book> {

    private static final long serialVersionUID = 723093698L;

    public static final QBook book = new QBook("book");

    public final StringPath author = createString("author");

    public final ListPath<com.nhnacademy.bookstore.entity.bookCategory.BookCategory, com.nhnacademy.bookstore.entity.bookCategory.QBookCategory> bookCategoryList = this.<com.nhnacademy.bookstore.entity.bookCategory.BookCategory, com.nhnacademy.bookstore.entity.bookCategory.QBookCategory>createList("bookCategoryList", com.nhnacademy.bookstore.entity.bookCategory.BookCategory.class, com.nhnacademy.bookstore.entity.bookCategory.QBookCategory.class, PathInits.DIRECT2);

    public final ListPath<com.nhnacademy.bookstore.entity.bookImage.BookImage, com.nhnacademy.bookstore.entity.bookImage.QBookImage> bookImageList = this.<com.nhnacademy.bookstore.entity.bookImage.BookImage, com.nhnacademy.bookstore.entity.bookImage.QBookImage>createList("bookImageList", com.nhnacademy.bookstore.entity.bookImage.BookImage.class, com.nhnacademy.bookstore.entity.bookImage.QBookImage.class, PathInits.DIRECT2);

    public final ListPath<com.nhnacademy.bookstore.entity.bookTag.BookTag, com.nhnacademy.bookstore.entity.bookTag.QBookTag> bookTagList = this.<com.nhnacademy.bookstore.entity.bookTag.BookTag, com.nhnacademy.bookstore.entity.bookTag.QBookTag>createList("bookTagList", com.nhnacademy.bookstore.entity.bookTag.BookTag.class, com.nhnacademy.bookstore.entity.bookTag.QBookTag.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.ZonedDateTime> createdAt = createDateTime("createdAt", java.time.ZonedDateTime.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath isbn = createString("isbn");

    public final BooleanPath packing = createBoolean("packing");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final DateTimePath<java.time.ZonedDateTime> publishedDate = createDateTime("publishedDate", java.time.ZonedDateTime.class);

    public final StringPath publisher = createString("publisher");

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final NumberPath<Integer> sellingPrice = createNumber("sellingPrice", Integer.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QBook(String variable) {
        super(Book.class, forVariable(variable));
    }

    public QBook(Path<? extends Book> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBook(PathMetadata metadata) {
        super(Book.class, metadata);
    }

}

