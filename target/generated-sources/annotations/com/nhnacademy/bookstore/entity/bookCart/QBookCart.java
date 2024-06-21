package com.nhnacademy.bookstore.entity.bookCart;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookCart is a Querydsl query type for BookCart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookCart extends EntityPathBase<BookCart> {

    private static final long serialVersionUID = -481426878L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookCart bookCart = new QBookCart("bookCart");

    public final com.nhnacademy.bookstore.entity.book.QBook book;

    public final com.nhnacademy.bookstore.entity.cart.QCart cart;

    public final DateTimePath<java.time.ZonedDateTime> createdAt = createDateTime("createdAt", java.time.ZonedDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public QBookCart(String variable) {
        this(BookCart.class, forVariable(variable), INITS);
    }

    public QBookCart(Path<? extends BookCart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookCart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookCart(PathMetadata metadata, PathInits inits) {
        this(BookCart.class, metadata, inits);
    }

    public QBookCart(Class<? extends BookCart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.nhnacademy.bookstore.entity.book.QBook(forProperty("book")) : null;
        this.cart = inits.isInitialized("cart") ? new com.nhnacademy.bookstore.entity.cart.QCart(forProperty("cart"), inits.get("cart")) : null;
    }

}

