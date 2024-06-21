package com.nhnacademy.bookstore.entity.cart;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCart is a Querydsl query type for Cart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCart extends EntityPathBase<Cart> {

    private static final long serialVersionUID = -1088679134L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCart cart = new QCart("cart");

    public final ListPath<com.nhnacademy.bookstore.entity.bookCart.BookCart, com.nhnacademy.bookstore.entity.bookCart.QBookCart> bookCartList = this.<com.nhnacademy.bookstore.entity.bookCart.BookCart, com.nhnacademy.bookstore.entity.bookCart.QBookCart>createList("bookCartList", com.nhnacademy.bookstore.entity.bookCart.BookCart.class, com.nhnacademy.bookstore.entity.bookCart.QBookCart.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.nhnacademy.bookstore.entity.member.QMember member;

    public QCart(String variable) {
        this(Cart.class, forVariable(variable), INITS);
    }

    public QCart(Path<? extends Cart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCart(PathMetadata metadata, PathInits inits) {
        this(Cart.class, metadata, inits);
    }

    public QCart(Class<? extends Cart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.nhnacademy.bookstore.entity.member.QMember(forProperty("member")) : null;
    }

}

