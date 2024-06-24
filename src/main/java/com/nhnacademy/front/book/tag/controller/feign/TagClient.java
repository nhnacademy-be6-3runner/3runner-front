package com.nhnacademy.front.book.tag.controller.feign;

import com.nhnacademy.front.book.tag.dto.response.TagResponse;
import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "BookTagClient", url ="http://${feign.client.url}/bookstore/tags")
public interface TagClient {
    @GetMapping
    ApiResponse<List<TagResponse>> readAllTagSet();
}
