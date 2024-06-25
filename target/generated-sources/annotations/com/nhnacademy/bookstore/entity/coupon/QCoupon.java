package com.nhnacademy.bookstore.entity.coupon;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoupon is a Querydsl query type for Coupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoupon extends EntityPathBase<Coupon> {

    private static final long serialVersionUID = 1980408546L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCoupon coupon = new QCoupon("coupon");

    public final EnumPath<com.nhnacademy.bookstore.entity.coupon.enums.CouponStatus> coupon_status = createEnum("coupon_status", com.nhnacademy.bookstore.entity.coupon.enums.CouponStatus.class);

    public final NumberPath<Long> couponFormId = createNumber("couponFormId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.ZonedDateTime> issuedAt = createDateTime("issuedAt", java.time.ZonedDateTime.class);

    public final com.nhnacademy.bookstore.entity.member.QMember member;

    public final ListPath<com.nhnacademy.bookstore.entity.purchaseCoupon.PurchaseCoupon, com.nhnacademy.bookstore.entity.purchaseCoupon.QPurchaseCoupon> purchaseCouponList = this.<com.nhnacademy.bookstore.entity.purchaseCoupon.PurchaseCoupon, com.nhnacademy.bookstore.entity.purchaseCoupon.QPurchaseCoupon>createList("purchaseCouponList", com.nhnacademy.bookstore.entity.purchaseCoupon.PurchaseCoupon.class, com.nhnacademy.bookstore.entity.purchaseCoupon.QPurchaseCoupon.class, PathInits.DIRECT2);

    public QCoupon(String variable) {
        this(Coupon.class, forVariable(variable), INITS);
    }

    public QCoupon(Path<? extends Coupon> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoupon(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoupon(PathMetadata metadata, PathInits inits) {
        this(Coupon.class, metadata, inits);
    }

    public QCoupon(Class<? extends Coupon> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.nhnacademy.bookstore.entity.member.QMember(forProperty("member")) : null;
    }

}

