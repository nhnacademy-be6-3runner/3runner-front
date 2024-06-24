package com.nhnacademy.bookstore.entity.bookCategory;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookCategory is a Querydsl query type for BookCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookCategory extends EntityPathBase<BookCategory> {

    private static final long serialVersionUID = 1182542978L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookCategory bookCategory = new QBookCategory("bookCategory");

    public final com.nhnacademy.bookstore.entity.book.QBook book;

    public final com.nhnacademy.bookstore.entity.category.QCategory category;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QBookCategory(String variable) {
        this(BookCategory.class, forVariable(variable), INITS);
    }

    public QBookCategory(Path<? extends BookCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookCategory(PathMetadata metadata, PathInits inits) {
        this(BookCategory.class, metadata, inits);
    }

    public QBookCategory(Class<? extends BookCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.nhnacademy.bookstore.entity.book.QBook(forProperty("book")) : null;
        this.category = inits.isInitialized("category") ? new com.nhnacademy.bookstore.entity.category.QCategory(forProperty("category"), inits.get("category")) : null;
    }

}

