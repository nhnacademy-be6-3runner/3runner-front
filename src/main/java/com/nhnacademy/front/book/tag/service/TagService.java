package com.nhnacademy.front.book.tag.service;

import com.nhnacademy.front.book.tag.dto.response.TagResponse;

import java.util.List;

public interface TagService {
    List<TagResponse> readAllBookTags();
}
