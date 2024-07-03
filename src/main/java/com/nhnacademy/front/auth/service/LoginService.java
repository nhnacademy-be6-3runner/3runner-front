package com.nhnacademy.front.auth.service;

import com.nhnacademy.front.auth.dto.request.LoginRequest;
import com.nhnacademy.front.auth.dto.response.LoginResponse;

/**
 * 로그인 서비스 인터페이스
 *
 * @author 오연수
 */
public interface LoginService {

	/**
	 * 로그인 요청을 보내고 응답 받는다.
	 *
	 * @param loginRequest 로그인 요청
	 * @return 로그인 응답
	 */
	LoginResponse getLoginResponse(LoginRequest loginRequest);

	/**
	 * 로그인 상태 반환한다.
	 *
	 * @return 로그인 되어 있는지 유무
	 */
	boolean checkLoginStatus();

	/**
	 * 로그아웃 한다.
	 */
	void logout();
}
