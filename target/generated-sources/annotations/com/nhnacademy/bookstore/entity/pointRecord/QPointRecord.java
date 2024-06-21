package com.nhnacademy.bookstore.entity.pointRecord;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPointRecord is a Querydsl query type for PointRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPointRecord extends EntityPathBase<PointRecord> {

    private static final long serialVersionUID = 868259912L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPointRecord pointRecord = new QPointRecord("pointRecord");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.ZonedDateTime> created_at = createDateTime("created_at", java.time.ZonedDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.nhnacademy.bookstore.entity.member.QMember member;

    public final NumberPath<Long> member_point = createNumber("member_point", Long.class);

    public final NumberPath<Long> remnant_point = createNumber("remnant_point", Long.class);

    public QPointRecord(String variable) {
        this(PointRecord.class, forVariable(variable), INITS);
    }

    public QPointRecord(Path<? extends PointRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPointRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPointRecord(PathMetadata metadata, PathInits inits) {
        this(PointRecord.class, metadata, inits);
    }

    public QPointRecord(Class<? extends PointRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.nhnacademy.bookstore.entity.member.QMember(forProperty("member")) : null;
    }

}

