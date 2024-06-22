package com.nhnacademy.bookstore.entity.bookLike;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookLike is a Querydsl query type for BookLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookLike extends EntityPathBase<BookLike> {

    private static final long serialVersionUID = 552343586L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookLike bookLike = new QBookLike("bookLike");

    public final com.nhnacademy.bookstore.entity.book.QBook book;

    public final DateTimePath<java.time.ZonedDateTime> createdAt = createDateTime("createdAt", java.time.ZonedDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.nhnacademy.bookstore.entity.member.QMember member;

    public QBookLike(String variable) {
        this(BookLike.class, forVariable(variable), INITS);
    }

    public QBookLike(Path<? extends BookLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookLike(PathMetadata metadata, PathInits inits) {
        this(BookLike.class, metadata, inits);
    }

    public QBookLike(Class<? extends BookLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.nhnacademy.bookstore.entity.book.QBook(forProperty("book")) : null;
        this.member = inits.isInitialized("member") ? new com.nhnacademy.bookstore.entity.member.QMember(forProperty("member")) : null;
    }

}

