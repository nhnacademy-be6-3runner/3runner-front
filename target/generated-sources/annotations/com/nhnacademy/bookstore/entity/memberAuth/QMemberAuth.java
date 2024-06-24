package com.nhnacademy.bookstore.entity.memberAuth;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberAuth is a Querydsl query type for MemberAuth
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberAuth extends EntityPathBase<MemberAuth> {

    private static final long serialVersionUID = -763871902L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberAuth memberAuth = new QMemberAuth("memberAuth");

    public final com.nhnacademy.bookstore.entity.auth.QAuth auth;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.nhnacademy.bookstore.entity.member.QMember member;

    public QMemberAuth(String variable) {
        this(MemberAuth.class, forVariable(variable), INITS);
    }

    public QMemberAuth(Path<? extends MemberAuth> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberAuth(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberAuth(PathMetadata metadata, PathInits inits) {
        this(MemberAuth.class, metadata, inits);
    }

    public QMemberAuth(Class<? extends MemberAuth> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auth = inits.isInitialized("auth") ? new com.nhnacademy.bookstore.entity.auth.QAuth(forProperty("auth")) : null;
        this.member = inits.isInitialized("member") ? new com.nhnacademy.bookstore.entity.member.QMember(forProperty("member")) : null;
    }

}

