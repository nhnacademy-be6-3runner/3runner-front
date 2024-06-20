package com.nhnacademy.front.book.book.controller.feign;

import com.nhnacademy.front.book.book.dto.request.CreateBookRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value="3runner-bookstore", path="/books")

public interface BookClient {
    @PostMapping()
    void createBook(@RequestBody @Valid CreateBookRequest book);

}
