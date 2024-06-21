package com.nhnacademy.bookstore.entity.purchaseCoupon;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchaseCoupon is a Querydsl query type for PurchaseCoupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchaseCoupon extends EntityPathBase<PurchaseCoupon> {

    private static final long serialVersionUID = -571824958L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchaseCoupon purchaseCoupon = new QPurchaseCoupon("purchaseCoupon");

    public final com.nhnacademy.bookstore.entity.coupon.QCoupon coupon;

    public final NumberPath<Integer> discountPrice = createNumber("discountPrice", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.nhnacademy.bookstore.entity.purchase.QPurchase purchase;

    public QPurchaseCoupon(String variable) {
        this(PurchaseCoupon.class, forVariable(variable), INITS);
    }

    public QPurchaseCoupon(Path<? extends PurchaseCoupon> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPurchaseCoupon(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPurchaseCoupon(PathMetadata metadata, PathInits inits) {
        this(PurchaseCoupon.class, metadata, inits);
    }

    public QPurchaseCoupon(Class<? extends PurchaseCoupon> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coupon = inits.isInitialized("coupon") ? new com.nhnacademy.bookstore.entity.coupon.QCoupon(forProperty("coupon"), inits.get("coupon")) : null;
        this.purchase = inits.isInitialized("purchase") ? new com.nhnacademy.bookstore.entity.purchase.QPurchase(forProperty("purchase"), inits.get("purchase")) : null;
    }

}

