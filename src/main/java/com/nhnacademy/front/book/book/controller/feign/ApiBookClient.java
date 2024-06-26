package com.nhnacademy.front.book.book.controller.feign;

import com.nhnacademy.front.book.book.dto.request.CreateBookRequest;
import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ApiBookClient", url ="http://${feign.client.url}/bookstore/api/books")
public interface ApiBookClient {
    @GetMapping("/{isbnId}")
    ApiResponse<Void> createApiBook(@PathVariable String isbnId);

}
