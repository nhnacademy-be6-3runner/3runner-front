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
		Logger logger = LoggerFactory.getLogger("racingcar");
		logger.debug("Hello world. debug");
		logger.warn("Hello world. warn");
		logger.error("Hello world. error");
		logger.info("Hello world. info");

		log.info("This is an info message");
		log.error("This is an error message");
		log.debug("This is a debug message");
		return "test!";
	}

	@GetMapping("/exception")
	public String exception() {
		throw new RuntimeException("Test exception");
	}

}
