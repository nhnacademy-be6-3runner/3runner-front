package com.nhnacademy.front.book.tag.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nhnacademy.front.book.tag.dto.response.TagResponse;

public interface TagService {
	List<TagResponse> readAllBookTags();

	Page<TagResponse> readAllAdminBookTags(int page, int size);

	void createTag(String name);
}
