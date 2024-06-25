package com.nhnacademy.bookstore.entity.simpleReview;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSimpleReview is a Querydsl query type for SimpleReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSimpleReview extends EntityPathBase<SimpleReview> {

    private static final long serialVersionUID = -540144286L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSimpleReview simpleReview = new QSimpleReview("simpleReview");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.ZonedDateTime> createdAt = createDateTime("createdAt", java.time.ZonedDateTime.class);

    public final com.nhnacademy.bookstore.entity.purchaseBook.QPurchaseBook purchaseBook;

    public final NumberPath<Long> purchaseBookId = createNumber("purchaseBookId", Long.class);

    public final DateTimePath<java.time.ZonedDateTime> updatedAt = createDateTime("updatedAt", java.time.ZonedDateTime.class);

    public QSimpleReview(String variable) {
        this(SimpleReview.class, forVariable(variable), INITS);
    }

    public QSimpleReview(Path<? extends SimpleReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSimpleReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSimpleReview(PathMetadata metadata, PathInits inits) {
        this(SimpleReview.class, metadata, inits);
    }

    public QSimpleReview(Class<? extends SimpleReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.purchaseBook = inits.isInitialized("purchaseBook") ? new com.nhnacademy.bookstore.entity.purchaseBook.QPurchaseBook(forProperty("purchaseBook"), inits.get("purchaseBook")) : null;
    }

}

