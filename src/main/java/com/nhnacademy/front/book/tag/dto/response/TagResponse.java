package com.nhnacademy.front.book.tag.dto.response;

import lombok.Builder;

/**
 * 태그 확인할 response.
 *
 * @author 한민기
 *
 * @param id    태그 아이디
 * @param name    태그 이름
 */
@Builder
public record TagResponse(
	long id,
	String name
) {
}
