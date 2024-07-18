package com.nhnacademy.front.book.tag.service.Impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.book.book.exception.InvalidApiResponseException;
import com.nhnacademy.front.book.tag.controller.feign.TagClient;
import com.nhnacademy.front.book.tag.dto.response.TagResponse;
import com.nhnacademy.front.book.tag.service.TagService;
import com.nhnacademy.front.util.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl implements TagService {

	private final TagClient tagClient;

	@Override
	public List<TagResponse> readAllBookTags() {
		log.info("readAllBookTags");

		ApiResponse<List<TagResponse>> api = tagClient.readAllTagSet();

		return api.getBody().getData();

	}

	/**
	 * 관리자 페이지용 태그 페이지 조회
	 * @param page        조회할 페이지
	 * @param size        죄회할 사이즈
	 * @return 태그 내용
	 */
	@Override
	public Page<TagResponse> readAllAdminBookTags(int page, int size) {
		ApiResponse<Page<TagResponse>> response = tagClient.readAllAdminTags(page, size);
		if (response.getHeader().isSuccessful() && response.getBody() != null) {
			return response.getBody().getData();
		} else {
			throw new InvalidApiResponseException("태그 페이지 조회 exception");
		}
	}

	/**
	 * 태그 추가
	 * @param name 태그 이름
	 */
	@Override
	public void createTag(String name) {
		tagClient.createTage(name);
	}

	/**
	 * 태그 삭제
	 * @param tagId 삭제할 태그 아이디
	 */
	@Override
	public void deleteTag(long tagId) {
		tagClient.deleteTage(tagId);
	}
}
