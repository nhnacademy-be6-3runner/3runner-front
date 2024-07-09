package com.nhnacademy.bookstore.book.bookLike.dto;

import lombok.Builder;

/**
 * 좋아요 dto 입니다.
 *
 * @param count 좋아요 갯
 *              
 * @param liked 좋아요 여부
 * @author 김은비
 */
@Builder
public record BookLikeResponse(Long count, boolean liked) {
}
