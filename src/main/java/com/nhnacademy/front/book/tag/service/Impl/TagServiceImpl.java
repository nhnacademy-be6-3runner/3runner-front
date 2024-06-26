package com.nhnacademy.front.book.tag.service.Impl;

import com.nhnacademy.front.book.tag.controller.feign.TagClient;
import com.nhnacademy.front.book.tag.dto.response.TagResponse;
import com.nhnacademy.front.book.tag.service.TagService;

import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
