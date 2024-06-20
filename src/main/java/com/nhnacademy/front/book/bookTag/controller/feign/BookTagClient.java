package com.nhnacademy.front.book.bookTag.controller.feign;

import com.nhnacademy.front.book.bookTag.dto.response.BookTagResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@FeignClient(value="3runner-bookstore", path="/tags")
public interface BookTagClient {
    @GetMapping
    Set<BookTagResponse> readAllTagSet();
}
