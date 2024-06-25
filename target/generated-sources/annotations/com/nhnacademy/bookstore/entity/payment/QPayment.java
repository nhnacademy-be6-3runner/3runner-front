package com.nhnacademy.bookstore.entity.payment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = -1582143278L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayment payment = new QPayment("payment");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.ZonedDateTime> paidAt = createDateTime("paidAt", java.time.ZonedDateTime.class);

    public final EnumPath<com.nhnacademy.bookstore.entity.payment.enums.PaymentStatus> paymentStatus = createEnum("paymentStatus", com.nhnacademy.bookstore.entity.payment.enums.PaymentStatus.class);

    public final com.nhnacademy.bookstore.entity.paymentType.QPaymentType paymentType;

    public final com.nhnacademy.bookstore.entity.purchase.QPurchase purchase;

    public QPayment(String variable) {
        this(Payment.class, forVariable(variable), INITS);
    }

    public QPayment(Path<? extends Payment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayment(PathMetadata metadata, PathInits inits) {
        this(Payment.class, metadata, inits);
    }

    public QPayment(Class<? extends Payment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.paymentType = inits.isInitialized("paymentType") ? new com.nhnacademy.bookstore.entity.paymentType.QPaymentType(forProperty("paymentType")) : null;
        this.purchase = inits.isInitialized("purchase") ? new com.nhnacademy.bookstore.entity.purchase.QPurchase(forProperty("purchase"), inits.get("purchase")) : null;
    }

}

