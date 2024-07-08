package com.nhnacademy.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {

	@GetMapping("/test")
	public String test() {
		Logger logger = LoggerFactory.getLogger("errorLogger");
		logger.error("3Runner 화이팅!");
		return "test!";
	}

	@GetMapping("/exception")
	public String exception() {
		throw new RuntimeException("Test exception");
	}

}
