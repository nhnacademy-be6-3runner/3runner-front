package com.nhnacademy.bookstore.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookstore")
public class TestController {
    @GetMapping()
    public String test() {
        return "Hello";
    }
}
