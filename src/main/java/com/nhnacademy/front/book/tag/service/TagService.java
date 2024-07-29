package com.nhnacademy.front.book.tag.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nhnacademy.front.book.tag.dto.response.TagResponse;

/**
 * 태그 관련 서비스.
 *
 * @author 한민기
 */
public interface TagService {
	/**
	 * 모든 태그 가져오기.
	 *
	 * @return 태그 리스트
	 */
	List<TagResponse> readAllBookTags();

	/**
	 * 모든 태그를 page 로 가져오기.
	 *
	 * @param page    페이지
	 * @param size    사이즈
	 * @return page 태그
	 */
	Page<TagResponse> readAllAdminBookTags(int page, int size);

	/**
	 * 태그 만들기.
	 *
	 * @param name 만들 태그의 이름
	 */
	void createTag(String name);

	/**
	 * 태그 삭제.
	 *
	 * @param tagId 삭제할 태그의 id
	 */
	void deleteTag(long tagId);
}
