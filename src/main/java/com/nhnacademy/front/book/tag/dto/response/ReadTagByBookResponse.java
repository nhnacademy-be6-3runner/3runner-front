package com.nhnacademy.front.book.tag.dto.response;

import lombok.Builder;

/**
 * 검색한 tag 정보 dto.
 *
 * @author 정주혁
 */
@Builder
public record ReadTagByBookResponse(Long id, String name) {
}
