package com.nhnacademy.front.token.service.impl;

import com.nhnacademy.front.token.adapter.TokenAdapter;
import com.nhnacademy.front.token.dto.request.RefreshRequest;
import com.nhnacademy.front.token.dto.response.RefreshResponse;
import com.nhnacademy.front.token.service.TokenService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

	private final TokenAdapter tokenAdapter;

	public TokenServiceImpl(TokenAdapter tokenAdapter) {
		this.tokenAdapter = tokenAdapter;
	}

	@Override
	public String requestNewAccessToken(String refreshToken) {
		ApiResponse<RefreshResponse> response = tokenAdapter.requestNewAccessToken(new RefreshRequest(refreshToken));
		if (!response.getHeader().isSuccessful()) {
			log.warn("재발급 요청 실패");
		}
		return response.getBody().getData().accessToken();
	}
}
