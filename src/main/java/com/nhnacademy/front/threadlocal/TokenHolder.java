package com.nhnacademy.front.threadlocal;

import lombok.extern.slf4j.Slf4j;

/**
 * Access Token 값을 관리하는 Utility Class
 */
@Slf4j
public class TokenHolder {
	private static final ThreadLocal<String> ACCESS_TOKEN = new ThreadLocal<>();
	private static final ThreadLocal<String> REFRESH_TOKEN = new ThreadLocal<>();

	/**
	 * 액세스 토큰 값을 가져온다.
	 *
	 * @return 액세스 토큰 값을 리턴한다.
	 */
	public static String getAccessToken() {
		return ACCESS_TOKEN.get();
	}

	/**
	 * 액세스 토큰 값을 설정한다.
	 *
	 * @param token 액세스 토큰 내용
	 */
	public static void setAccessToken(String token) {
		ACCESS_TOKEN.set(token);
	}

	/**
	 * 스레드 로컬을 리셋한다.
	 */
	public static void resetAccessToken() {
		ACCESS_TOKEN.remove();
	}

	public static String getRefreshToken() {
		return REFRESH_TOKEN.get();
	}

	public static void setRefreshToken(String token) {
		log.warn("Set refresh token {}", token);
		REFRESH_TOKEN.set(token);
	}

	public static void resetRefreshToken() {
		REFRESH_TOKEN.remove();
	}
}