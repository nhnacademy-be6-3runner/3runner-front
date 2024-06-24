package com.nhnacademy.front.threadlocal;

/**
 * Access Token 값을 관리하는 Utility Class
 */
public class TokenHolder {
	private static final ThreadLocal<String> ACCESS_TOKEN = new ThreadLocal<>();

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
	public static void reset() {
		ACCESS_TOKEN.remove();
	}
}