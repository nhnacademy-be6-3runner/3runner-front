package com.nhnacademy.front.book.book.controller.feign;

import com.nhnacademy.front.book.book.dto.request.CreateBookRequest;
import com.nhnacademy.front.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "BookClient", url ="http://${feign.client.url}/bookstore/books")
public interface BookClient {
    @PostMapping
    ApiResponse<Void> createBook(@RequestBody  CreateBookRequest createBookRequest);

}
