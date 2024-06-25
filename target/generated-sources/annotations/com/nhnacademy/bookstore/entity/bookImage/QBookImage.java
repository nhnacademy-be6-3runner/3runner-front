package com.nhnacademy.bookstore.entity.bookImage;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookImage is a Querydsl query type for BookImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookImage extends EntityPathBase<BookImage> {

    private static final long serialVersionUID = -1400130134L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookImage bookImage = new QBookImage("bookImage");

    public final com.nhnacademy.bookstore.entity.book.QBook book;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.nhnacademy.bookstore.entity.bookImage.enums.BookImageType> type = createEnum("type", com.nhnacademy.bookstore.entity.bookImage.enums.BookImageType.class);

    public final StringPath url = createString("url");

    public QBookImage(String variable) {
        this(BookImage.class, forVariable(variable), INITS);
    }

    public QBookImage(Path<? extends BookImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookImage(PathMetadata metadata, PathInits inits) {
        this(BookImage.class, metadata, inits);
    }

    public QBookImage(Class<? extends BookImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.nhnacademy.bookstore.entity.book.QBook(forProperty("book")) : null;
    }

}

