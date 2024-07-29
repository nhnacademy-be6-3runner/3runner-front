package com.nhnacademy.front.book.categroy.dto.response;

import lombok.Builder;

/**
 * 카테고리 내용 보여주기 response.
 *
 * @author 한민기
 *
 * @param id        아이디
 * @param name        이름
 */
@Builder
public record CategoryResponse(long id, String name) {
}
