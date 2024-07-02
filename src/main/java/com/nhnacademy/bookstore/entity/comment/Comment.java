package com.nhnacademy.bookstore.entity.comment;

import com.nhnacademy.bookstore.entity.comment.enums.CommentStatus;
import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.entity.review.Review;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String content;

    @NotNull
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime deletedAt;

    @NotNull
    private CommentStatus status;

    @ManyToOne
    @NotNull
    @Setter
    private Review review;

    @OneToOne
    private Member member;

    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }

    /**
     * 댓글 삭제 메서드입니다.
     *
     * @author 김은비
     */
    void deletedComment() {
        this.deletedAt = ZonedDateTime.now();
        this.status = CommentStatus.DELETE;
    }
}
