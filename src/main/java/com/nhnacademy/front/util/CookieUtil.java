package com.nhnacademy.front.util;

import jakarta.servlet.http.Cookie;

/**
 * 쿠키 유틸리티 클래스
 *
 * @author 오연수
 */
public class CookieUtil {

	/**
	 * 쿠키를 생성한다.
	 * 기본 유효 기간 1일
	 *
	 * @param key 쿠키 이름
	 * @param value 쿠키 값
	 * @return 해당 쿠키
	 */
	public static Cookie createCookie(String key, String value) {
		return createCookie(key, value, 24 * 60 * 60);
	}

	/**
	 * 쿠키를 생성한다.
	 *
	 * @param key 쿠키 이름
	 * @param value 쿠키 값
	 * @param maxAge 쿠키 유효 기간 (초 단위)
	 * @return 해당 쿠키
	 */
	public static Cookie createCookie(String key, String value, Integer maxAge) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(maxAge);
		cookie.setHttpOnly(true);
		cookie.setPath("/");

		return cookie;
	}
}
