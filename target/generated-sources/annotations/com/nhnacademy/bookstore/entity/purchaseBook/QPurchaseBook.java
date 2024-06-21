package com.nhnacademy.bookstore.entity.purchaseBook;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchaseBook is a Querydsl query type for PurchaseBook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchaseBook extends EntityPathBase<PurchaseBook> {

    private static final long serialVersionUID = 760437090L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchaseBook purchaseBook = new QPurchaseBook("purchaseBook");

    public final com.nhnacademy.bookstore.entity.book.QBook book;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final com.nhnacademy.bookstore.entity.purchase.QPurchase purchase;

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public QPurchaseBook(String variable) {
        this(PurchaseBook.class, forVariable(variable), INITS);
    }

    public QPurchaseBook(Path<? extends PurchaseBook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPurchaseBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPurchaseBook(PathMetadata metadata, PathInits inits) {
        this(PurchaseBook.class, metadata, inits);
    }

    public QPurchaseBook(Class<? extends PurchaseBook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.nhnacademy.bookstore.entity.book.QBook(forProperty("book")) : null;
        this.purchase = inits.isInitialized("purchase") ? new com.nhnacademy.bookstore.entity.purchase.QPurchase(forProperty("purchase"), inits.get("purchase")) : null;
    }

}

