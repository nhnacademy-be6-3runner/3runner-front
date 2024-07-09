package com.nhnacademy.front.book.bookLike.controller.feign;

import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "BookReview", url = "http://${feign.client.url}/bookstore")
public interface BookLikeClient {
    @GetMapping("/{bookId}/likes")
    ApiResponse<Long> countLikeByBookId(@PathVariable("bookId") Long bookId);
}
