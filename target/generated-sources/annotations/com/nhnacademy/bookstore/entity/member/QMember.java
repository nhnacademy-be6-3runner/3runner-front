package com.nhnacademy.bookstore.entity.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1550235746L;

    public static final QMember member = new QMember("member1");

    public final ListPath<com.nhnacademy.bookstore.entity.address.Address, com.nhnacademy.bookstore.entity.address.QAddress> addressList = this.<com.nhnacademy.bookstore.entity.address.Address, com.nhnacademy.bookstore.entity.address.QAddress>createList("addressList", com.nhnacademy.bookstore.entity.address.Address.class, com.nhnacademy.bookstore.entity.address.QAddress.class, PathInits.DIRECT2);

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final DateTimePath<java.time.ZonedDateTime> birthday = createDateTime("birthday", java.time.ZonedDateTime.class);

    public final DateTimePath<java.time.ZonedDateTime> created_at = createDateTime("created_at", java.time.ZonedDateTime.class);

    public final DateTimePath<java.time.ZonedDateTime> deleted_at = createDateTime("deleted_at", java.time.ZonedDateTime.class);

    public final StringPath email = createString("email");

    public final EnumPath<com.nhnacademy.bookstore.entity.member.enums.Grade> grade = createEnum("grade", com.nhnacademy.bookstore.entity.member.enums.Grade.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.ZonedDateTime> last_login_date = createDateTime("last_login_date", java.time.ZonedDateTime.class);

    public final ListPath<com.nhnacademy.bookstore.entity.memberAuth.MemberAuth, com.nhnacademy.bookstore.entity.memberAuth.QMemberAuth> memberAuthList = this.<com.nhnacademy.bookstore.entity.memberAuth.MemberAuth, com.nhnacademy.bookstore.entity.memberAuth.QMemberAuth>createList("memberAuthList", com.nhnacademy.bookstore.entity.memberAuth.MemberAuth.class, com.nhnacademy.bookstore.entity.memberAuth.QMemberAuth.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.ZonedDateTime> modified_at = createDateTime("modified_at", java.time.ZonedDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final NumberPath<Long> point = createNumber("point", Long.class);

    public final ListPath<com.nhnacademy.bookstore.entity.pointRecord.PointRecord, com.nhnacademy.bookstore.entity.pointRecord.QPointRecord> pointRecordList = this.<com.nhnacademy.bookstore.entity.pointRecord.PointRecord, com.nhnacademy.bookstore.entity.pointRecord.QPointRecord>createList("pointRecordList", com.nhnacademy.bookstore.entity.pointRecord.PointRecord.class, com.nhnacademy.bookstore.entity.pointRecord.QPointRecord.class, PathInits.DIRECT2);

    public final ListPath<com.nhnacademy.bookstore.entity.purchase.Purchase, com.nhnacademy.bookstore.entity.purchase.QPurchase> purchaseList = this.<com.nhnacademy.bookstore.entity.purchase.Purchase, com.nhnacademy.bookstore.entity.purchase.QPurchase>createList("purchaseList", com.nhnacademy.bookstore.entity.purchase.Purchase.class, com.nhnacademy.bookstore.entity.purchase.QPurchase.class, PathInits.DIRECT2);

    public final EnumPath<com.nhnacademy.bookstore.entity.member.enums.Status> status = createEnum("status", com.nhnacademy.bookstore.entity.member.enums.Status.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

