package com.nhnacademy.bookstore.entity.auth;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuth is a Querydsl query type for Auth
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuth extends EntityPathBase<Auth> {

    private static final long serialVersionUID = 159148066L;

    public static final QAuth auth = new QAuth("auth");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.nhnacademy.bookstore.entity.memberAuth.MemberAuth, com.nhnacademy.bookstore.entity.memberAuth.QMemberAuth> memberAuthSet = this.<com.nhnacademy.bookstore.entity.memberAuth.MemberAuth, com.nhnacademy.bookstore.entity.memberAuth.QMemberAuth>createList("memberAuthSet", com.nhnacademy.bookstore.entity.memberAuth.MemberAuth.class, com.nhnacademy.bookstore.entity.memberAuth.QMemberAuth.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public QAuth(String variable) {
        super(Auth.class, forVariable(variable));
    }

    public QAuth(Path<? extends Auth> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuth(PathMetadata metadata) {
        super(Auth.class, metadata);
    }

}

