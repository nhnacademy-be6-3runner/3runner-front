package com.nhnacademy.bookstore.entity.paymentType;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPaymentType is a Querydsl query type for PaymentType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentType extends EntityPathBase<PaymentType> {

    private static final long serialVersionUID = -191823226L;

    public static final QPaymentType paymentType = new QPaymentType("paymentType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QPaymentType(String variable) {
        super(PaymentType.class, forVariable(variable));
    }

    public QPaymentType(Path<? extends PaymentType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPaymentType(PathMetadata metadata) {
        super(PaymentType.class, metadata);
    }

}

