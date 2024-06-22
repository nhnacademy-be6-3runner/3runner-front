package com.nhnacademy.bookstore.entity.purchase;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchase is a Querydsl query type for Purchase
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchase extends EntityPathBase<Purchase> {

    private static final long serialVersionUID = 1117963586L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchase purchase = new QPurchase("purchase");

    public final DateTimePath<java.time.ZonedDateTime> createdAt = createDateTime("createdAt", java.time.ZonedDateTime.class);

    public final NumberPath<Integer> deliveryPrice = createNumber("deliveryPrice", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.nhnacademy.bookstore.entity.member.QMember member;

    public final EnumPath<com.nhnacademy.bookstore.entity.purchase.enums.MemberType> memberType = createEnum("memberType", com.nhnacademy.bookstore.entity.purchase.enums.MemberType.class);

    public final ComparablePath<java.util.UUID> orderNumber = createComparable("orderNumber", java.util.UUID.class);

    public final StringPath password = createString("password");

    public final com.nhnacademy.bookstore.entity.pointRecord.QPointRecord pointRecord;

    public final ListPath<com.nhnacademy.bookstore.entity.purchaseBook.PurchaseBook, com.nhnacademy.bookstore.entity.purchaseBook.QPurchaseBook> purchaseBookList = this.<com.nhnacademy.bookstore.entity.purchaseBook.PurchaseBook, com.nhnacademy.bookstore.entity.purchaseBook.QPurchaseBook>createList("purchaseBookList", com.nhnacademy.bookstore.entity.purchaseBook.PurchaseBook.class, com.nhnacademy.bookstore.entity.purchaseBook.QPurchaseBook.class, PathInits.DIRECT2);

    public final ListPath<com.nhnacademy.bookstore.entity.purchaseCoupon.PurchaseCoupon, com.nhnacademy.bookstore.entity.purchaseCoupon.QPurchaseCoupon> purchaseCouponList = this.<com.nhnacademy.bookstore.entity.purchaseCoupon.PurchaseCoupon, com.nhnacademy.bookstore.entity.purchaseCoupon.QPurchaseCoupon>createList("purchaseCouponList", com.nhnacademy.bookstore.entity.purchaseCoupon.PurchaseCoupon.class, com.nhnacademy.bookstore.entity.purchaseCoupon.QPurchaseCoupon.class, PathInits.DIRECT2);

    public final StringPath road = createString("road");

    public final EnumPath<com.nhnacademy.bookstore.entity.purchase.enums.PurchaseStatus> status = createEnum("status", com.nhnacademy.bookstore.entity.purchase.enums.PurchaseStatus.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public QPurchase(String variable) {
        this(Purchase.class, forVariable(variable), INITS);
    }

    public QPurchase(Path<? extends Purchase> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPurchase(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPurchase(PathMetadata metadata, PathInits inits) {
        this(Purchase.class, metadata, inits);
    }

    public QPurchase(Class<? extends Purchase> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.nhnacademy.bookstore.entity.member.QMember(forProperty("member")) : null;
        this.pointRecord = inits.isInitialized("pointRecord") ? new com.nhnacademy.bookstore.entity.pointRecord.QPointRecord(forProperty("pointRecord"), inits.get("pointRecord")) : null;
    }

}

