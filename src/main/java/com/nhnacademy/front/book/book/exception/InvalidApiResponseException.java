package com.nhnacademy.front.book.book.exception;

/**
 * api response exception 입니다.
 *
 * @author 김은비
 */
public class InvalidApiResponseException extends RuntimeException {
	public InvalidApiResponseException(String message) {
		super("Invalid API Response: " + message);
	}
}
