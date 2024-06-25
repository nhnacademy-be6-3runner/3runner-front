package com.nhnacademy.bookstore.entity.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = -17529182L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final ListPath<com.nhnacademy.bookstore.entity.comment.Comment, com.nhnacademy.bookstore.entity.comment.QComment> commentList = this.<com.nhnacademy.bookstore.entity.comment.Comment, com.nhnacademy.bookstore.entity.comment.QComment>createList("commentList", com.nhnacademy.bookstore.entity.comment.Comment.class, com.nhnacademy.bookstore.entity.comment.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.ZonedDateTime> createdAt = createDateTime("createdAt", java.time.ZonedDateTime.class);

    public final DateTimePath<java.time.ZonedDateTime> deletedAt = createDateTime("deletedAt", java.time.ZonedDateTime.class);

    public final StringPath deletedReason = createString("deletedReason");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.nhnacademy.bookstore.entity.purchaseBook.QPurchaseBook purchaseBook;

    public final NumberPath<Double> rating = createNumber("rating", Double.class);

    public final ListPath<com.nhnacademy.bookstore.entity.reviewImage.ReviewImage, com.nhnacademy.bookstore.entity.reviewImage.QReviewImage> reviewImageList = this.<com.nhnacademy.bookstore.entity.reviewImage.ReviewImage, com.nhnacademy.bookstore.entity.reviewImage.QReviewImage>createList("reviewImageList", com.nhnacademy.bookstore.entity.reviewImage.ReviewImage.class, com.nhnacademy.bookstore.entity.reviewImage.QReviewImage.class, PathInits.DIRECT2);

    public final ListPath<com.nhnacademy.bookstore.entity.reviewLike.ReviewLike, com.nhnacademy.bookstore.entity.reviewLike.QReviewLike> reviewLikeList = this.<com.nhnacademy.bookstore.entity.reviewLike.ReviewLike, com.nhnacademy.bookstore.entity.reviewLike.QReviewLike>createList("reviewLikeList", com.nhnacademy.bookstore.entity.reviewLike.ReviewLike.class, com.nhnacademy.bookstore.entity.reviewLike.QReviewLike.class, PathInits.DIRECT2);

    public final EnumPath<com.nhnacademy.bookstore.entity.review.enums.ReviewStatus> reviewStatus = createEnum("reviewStatus", com.nhnacademy.bookstore.entity.review.enums.ReviewStatus.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.ZonedDateTime> updatedAt = createDateTime("updatedAt", java.time.ZonedDateTime.class);

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.purchaseBook = inits.isInitialized("purchaseBook") ? new com.nhnacademy.bookstore.entity.purchaseBook.QPurchaseBook(forProperty("purchaseBook"), inits.get("purchaseBook")) : null;
    }

}

